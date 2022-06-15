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

abstract class AbstractScheduledTask
{
    public abstract function executeAction($plugin);

}

