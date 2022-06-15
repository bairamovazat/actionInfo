<?php

/**
 * @file classes/StaticPage.inc.php
 *
 * Copyright (c) 2014-2020 Simon Fraser University
 * Copyright (c) 2003-2020 John Willinsky
 * Distributed under the GNU GPL v3. For full terms see the file docs/COPYING.
 *
 * @package plugins.generic.staticPages
 * @class StaticPage
 * Data object representing a static page.
 */

abstract class AbstractHandler
{
    private $user;
    private $context;

    abstract function getHandlerName();

    abstract function process($hookName, $args);

    public function init($hookName, $args)
    {
        $request = Application::get()->getRequest();
        $this->context = $request->getContext();
        $this->user =& $request->getUser();

        $this->process($hookName, $args);

    }

    public function getUser()
    {
        return $this->user;
    }

    public function getContext()
    {
        return $this->context;
    }

    public function saveActionInfo($type, $action, $params, $payload)
    {
        /** @var ActionInfoDAO */
        $actionInfoDao =& DAORegistry::getDAO('ActionInfoDAO');
        /** @var ActionInfo */
        $actionInfo = $actionInfoDao->newDataObject();

        $actionInfo->setUserId($this->user == null ? 0 : $this->user->getId());
        $actionInfo->setContextId($this->context == null ? 0 : $this->context->getId());
        $actionInfo->setDate(time());
        $actionInfo->setType($type);
        $actionInfo->setAction($action);
        $actionInfo->setParams($params);
        $actionInfo->setPayload($payload);

        $actionInfoDao->insertObject($actionInfo);
    }


}

