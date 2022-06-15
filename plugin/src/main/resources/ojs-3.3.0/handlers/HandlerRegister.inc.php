<?php

import('plugins.generic.actionInfo.handlers.LoadHandler');

class HandlerRegister
{

    function getHandlerList()
    {
        $handlers = array();

        $handlers[] = new LoadHandler();

        return $handlers;
    }

}