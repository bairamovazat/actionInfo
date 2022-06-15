<?php

import('lib.pkp.classes.plugins.GenericPlugin');
import('plugins.generic.actionInfo.handlers.HandlerRegister');


class ActionInfoPlugin extends GenericPlugin
{

    public function register($category, $path, $mainContextId = NULL)
    {

        $register_success = parent::register($category, $path, $mainContextId);

        HookRegistry::register('AcronPlugin::parseCronTab', array($this, 'callbackParseCronTab'));

        if ($register_success) {
            if ($this->getEnabled($mainContextId)) {
                // Register the static pages DAO.
                import('plugins.generic.actionInfo.classes.ActionInfoDAO');
                $staticPagesDao = new ActionInfoDAO();
                DAORegistry::registerDAO('ActionInfoDAO', $staticPagesDao);


                $handlerRegister = new HandlerRegister();

                $handlers = $handlerRegister->getHandlerList();

                foreach ($handlers as $handler) {
                    HookRegistry::register($handler->getHandlerName(), [$handler, 'init']);
                }

            }
            return true;
        }
        return false;
    }


    function sendRequestToCentralServer($url, $data)
    {
        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type:application/json'));
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data, JSON_UNESCAPED_UNICODE));
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_HEADER, false);
        $res = curl_exec($ch);
        $httpcode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);
        $res = json_encode($res, JSON_UNESCAPED_UNICODE);
        return ["code"=> $httpcode, "result"=>$res ];
    }

    function callbackParseCronTab($hookName, $args)
    {
        if ($this->getEnabled() || !Config::getVar('general', 'installed')) {
            $taskFilesPath =& $args[0]; // Reference needed.
            $taskFilesPath[] = $this->getPluginPath() . DIRECTORY_SEPARATOR . 'scheduledTask.xml';
        }
        return false;
    }

    public function log($msg)
    {
        error_log($msg);
    }

    /**
     * @copydoc Plugin::getInstallMigration()
     */
    public function getInstallMigration()
    {
        $this->import('ActionInfoSchemaMigration');
        return new ActionInfoSchemaMigration();
    }

    function getDisplayName()
    {

        return "ActionInfoPlugin";

    }

    function getDescription()
    {

        return "ActionInfoPlugin";

    }

}