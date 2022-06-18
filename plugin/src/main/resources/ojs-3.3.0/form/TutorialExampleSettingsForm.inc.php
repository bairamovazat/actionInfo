<?php

import('lib.pkp.classes.form.Form');
import('plugins.generic.actionInfo.settings.PluginSetting');
import('plugins.generic.actionInfo.handlers.HandlerRegister');
import('plugins.generic.actionInfo.scheduled.ScheduledTaskRegister');

class TutorialExampleSettingsForm extends Form {

    /** @var TutorialExamplePlugin  */
    public $plugin;

    private $settings = [];

    public function __construct($plugin) {

        // Define the settings template and store a copy of the plugin object
        parent::__construct($plugin->getTemplateResource('settings.tpl'));
        $this->plugin = $plugin;

        // Always add POST and CSRF validation to secure your form.
        $this->addCheck(new FormValidatorPost($this));
        $this->addCheck(new FormValidatorCSRF($this));

        $handlers = array_merge(HandlerRegister::getHandlerList(), ScheduledTaskRegister::getScheduledTaskList());

        foreach ($handlers as $handler) {
            foreach ($handler->getSettings() as $setting) {
                $this->settings[$setting->getId()] = $setting;
            }
        }

    }

    /**
     * Load settings already saved in the database
     *
     * Settings are stored by context, so that each journal or press
     * can have different settings.
     */
    public function initData() {
        parent::initData();
    }

    /**
     * Load data that was submitted with the form
     */
    public function readInputData() {
        $vars = [];
        foreach ($this->settings as $setting) {
            $vars[] = $setting->getId();
        }
        $this->readUserVars($vars);
        parent::readInputData();
    }

    /**
     * Fetch any additional data needed for your form.
     *
     * Data assigned to the form using $this->setData() during the
     * initData() or readInputData() methods will be passed to the
     * template.
     */
    public function fetch($request, $template = null, $display = false) {

        // Pass the plugin name to the template so that it can be
        // used in the URL that the form is submitted to

        $templateMgr = TemplateManager::getManager($request);
        $templateMgr->assign('plugin', $this->plugin);
        $templateMgr->assign('pluginName', $this->plugin->getName());
        $templateMgr->assign('settings', $this->settings);

        return parent::fetch($request, $template, $display);
    }

    /**
     * Save the settings
     */
    public function execute(...$functionArgs) {
        $contextId = Application::get()->getRequest()->getContext()->getId();

        foreach ($this->settings as $setting) {
            $setting->setValue($this->plugin, $this->getData($setting->getId()));
        }

        // Tell the user that the save was successful.
        import('classes.notification.NotificationManager');
        $notificationMgr = new NotificationManager();
        $notificationMgr->createTrivialNotification(
            Application::get()->getRequest()->getUser()->getId(),
            NOTIFICATION_TYPE_SUCCESS,
            ['contents' => __('common.changesSaved')]
        );

        return parent::execute(...$functionArgs);
    }
}