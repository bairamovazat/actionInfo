<?php

import('plugins.generic.actionInfo.classes.AbstractHandler');


class LoadHandler extends AbstractHandler
{

    function getHandlerName()
    {
        return "LoadHandler";
    }

    function process($hookName, $args)
    {
        $page =& $args[0];
        $op =& $args[1];
        $sourceFile =& $args[2];

        $user = $this->getUser();
        $context = $this->getContext();

        $request = Application::get()->getRequest();
        $url = $request->getRequestPath();

        if (strpos($url, 'notification/fetchNotification') !== false ||
            strpos($url, 'management/settings') !== false) {
            return;
        }

        if ($user != null ) {
            $this->saveActionInfo("PAGE_LOAD", $url, $request->getQueryString(), "");
        }
    }

}