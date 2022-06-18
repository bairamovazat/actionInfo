<?php

import('plugins.generic.actionInfo.handlers.LoadHandler');

class HandlerRegister
{

    public static function getHandlerList()
    {
        $handlers = array();

        $handlers[] = new LoadHandler();

        return $handlers;
    }

}