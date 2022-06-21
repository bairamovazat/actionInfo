<?php

import('lib.pkp.classes.plugins.GenericPlugin');
import('plugins.generic.actionInfo.handlers.HandlerRegister');
import('plugins.generic.actionInfo.form.TutorialExampleSettingsForm');


class ActionInfoPlugin extends GenericPlugin
{

    private $handlers = [];

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


                $this->handlers = HandlerRegister::getHandlerList();

                foreach ($this->handlers as $handler) {
                    HookRegistry::register($handler->getHandlerName(), [$this, 'hookCallback']);
                }

            }
            return true;
        }
        return false;
    }

    function hookCallback($hookName, $args) {
        foreach ($this->handlers as $handler) {
            if ($hookName == $handler->getHandlerName()) {
                $handler->init($hookName, $args, $this);
            }
        }
    }

    function sendRequestToCentralServer($url, $data, $token)
    {
        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type:application/json', 'X-AUTH-TOKEN:'.$token));

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

    public function manage($args, $request) {
        switch ($request->getUserVar('verb')) {
            case 'settings':

                // Load the custom form
                $form = new TutorialExampleSettingsForm($this);

                // Fetch the form the first time it loads, before
                // the user has tried to save it
                if (!$request->getUserVar('save')) {
                    $form->initData();
                    return new JSONMessage(true, $form->fetch($request));
                }

                // Validate and execute the form
                $form->readInputData();
                if ($form->validate()) {
                    $form->execute();
                    return new JSONMessage(true);
                }
        }
        return parent::manage($args, $request);
    }

    public function getActions($request, $actionArgs) {

        // Get the existing actions
        $actions = parent::getActions($request, $actionArgs);
        if (!$this->getEnabled()) {
            return $actions;
        }

        // Create a LinkAction that will call the plugin's
        // `manage` method with the `settings` verb.
        $router = $request->getRouter();
        import('lib.pkp.classes.linkAction.request.AjaxModal');
        $linkAction = new LinkAction(
            'settings',
            new AjaxModal(
                $router->url(
                    $request,
                    null,
                    null,
                    'manage',
                    null,
                    array(
                        'verb' => 'settings',
                        'plugin' => $this->getName(),
                        'category' => 'generic'
                    )
                ),
                $this->getDisplayName()
            ),
            __('manager.plugins.settings'),
            null
        );

        // Add the LinkAction to the existing actions.
        // Make it the first action to be consistent with
        // other plugins.
        array_unshift($actions, $linkAction);

        return $actions;
    }

}