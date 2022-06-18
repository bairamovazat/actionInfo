<?php

import('plugins.generic.actionInfo.classes.AbstractConfigurable');

abstract class AbstractScheduledTask extends AbstractConfigurable
{
    public abstract function executeAction($plugin);

}

