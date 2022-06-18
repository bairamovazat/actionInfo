<?php

import('plugins.generic.actionInfo.classes.AbstractConfigurable');

abstract class AbstractHandler extends AbstractConfigurable
{
    private $user;
    private $context;
    abstract function getHandlerName();

    abstract function process($hookName, $args, $plugin);

    public function init($hookName, $args, $plugin)
    {
        $request = Application::get()->getRequest();
        $this->context = $request->getContext();
        $this->user =& $request->getUser();

        $this->process($hookName, $args, $plugin);
    }

    public function getUser()
    {
        return $this->user;
    }

    public function getContext()
    {
        return $this->context;
    }

    public function saveActionInfo($type, $action, $params, $payload, $date)
    {
        /** @var ActionInfoDAO */
        $actionInfoDao =& DAORegistry::getDAO('ActionInfoDAO');
        /** @var ActionInfo */
        $actionInfo = $actionInfoDao->newDataObject();

        $actionInfo->setUserId($this->user == null ? 0 : $this->user->getId());
        $actionInfo->setContextId($this->context == null ? 0 : $this->context->getId());
        $actionInfo->setDate($date);
        $actionInfo->setType($type);
        $actionInfo->setAction($action);
        $actionInfo->setParams($params);
        $actionInfo->setPayload($payload);

        $actionInfoDao->insertObject($actionInfo);
    }

}

