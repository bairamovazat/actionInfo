<?php

import('plugins.generic.actionInfo.scheduled.CentralServerScheduledTask');
import('plugins.generic.actionInfo.scheduled.SubmissionScheduledTask');

class ScheduledTaskRegister
{

    function getScheduledTaskList()
    {
        $tasks = array();

        $tasks[] = new SubmissionScheduledTask();

        //Должен быть последним
        $tasks[] = new CentralServerScheduledTask();

        return $tasks;
    }

}