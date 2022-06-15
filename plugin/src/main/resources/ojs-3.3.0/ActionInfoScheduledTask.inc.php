<?php

import('lib.pkp.classes.scheduledTask.ScheduledTask');
import('plugins.generic.actionInfo.scheduled.ScheduledTaskRegister');


class ActionInfoScheduledTask extends ScheduledTask
{

    /** @var ActionInfoPlugin $_plugin */
    public $_plugin;

    /**
     * Constructor.
     */
    function __construct($args)
    {
        PluginRegistry::loadCategory('generic');
        $plugin = PluginRegistry::getPlugin('generic', 'actioninfoplugin'); /** @var ActionInfoPlugin $plugin */
        $this->_plugin = $plugin;

        error_log("ActionInfoPlugin init");

        if (is_a($plugin, 'ActionInfoPlugin')) {
//            $plugin->addLocaleData();
            error_log("ActionInfoPlugin init success");
        }


        parent::__construct($args);
    }

    /**
     * @copydoc ScheduledTask::getName()
     */
    function getName() {
        return "Schedule task";
    }

    /**
     * @copydoc ScheduledTask::executeActions()
     */
    public function executeActions()
    {

        if (!$this->_plugin) return false;

        $plugin = $this->_plugin;

        $scheduledTaskRegister = new ScheduledTaskRegister();

        foreach ($scheduledTaskRegister->getScheduledTaskList() as $task) {
            try {
                $task->executeAction($plugin);
            } catch (Exception $ex) {
                error_log($ex->getMessage());
                error_log($ex->getTraceAsString());
            }
        }

        return true;
    }

}