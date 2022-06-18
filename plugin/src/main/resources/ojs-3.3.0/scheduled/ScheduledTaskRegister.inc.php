<?php

import('plugins.generic.actionInfo.scheduled.CentralServerScheduledTask');
import('plugins.generic.actionInfo.scheduled.SubmissionScheduledTask');

class ScheduledTaskRegister
{

    public static function getScheduledTaskList()
    {
        $tasks = array();

        $tasks[] = new SubmissionScheduledTask();

        $tasks[] = new CentralServerScheduledTask();

        return $tasks;
    }

}