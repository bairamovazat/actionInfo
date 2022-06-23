<?php

import('plugins.generic.actionInfo.classes.AbstractScheduledTask');


class SubmissionScheduledTask extends AbstractScheduledTask
{
    private $plugin;

    public function executeAction($plugin)
    {
        $this->plugin = $plugin;

        $lastID = $this->getLastSubmissionId();

        $submissions = $this->getNewSubmission($lastID);

        $newLastId = $lastID;

        while ($submission = $submissions->next()) {
            if ($newLastId < $submission->getId()) {
                $newLastId = $submission->getId();
            }
            if ($submission->getStatus() == 1) {
                continue;
            }

            $user = $submission->getCurrentPublication()->getPrimaryAuthor();

            $userId = null;

            if ($user != null) {
                $userId = $user->getId();
            }

            $date = 0;

            if ($submission->getDateSubmitted() != null) {
                $date = strtotime($submission->getDateSubmitted());
            }

            $this->saveActionInfo("SUBMISSION", $submission->getStatusKey(), "",
                json_encode($submission, JSON_UNESCAPED_UNICODE), $userId,  $submission->getJournalId(), $date);
        }
        $this->setLastSubmissionId($newLastId);
    }

    function getNewSubmission($start, $contextId = null) {

        $submissionDao = DAORegistry::getDAO('SubmissionDAO'); /* @var $submissionDao SubmissionDAO */

        $params = [
            $start
        ];
        if ($contextId) $params[] = (int) $contextId;

        $result = $submissionDao->retrieve(
            'SELECT * from submissions where submission_id > ?'
            . ($contextId ? ' AND s.context_id = ?' : ''),
            $params
        );

        return new DAOResultFactory($result, $submissionDao, '_fromRow');
    }

    function getLastSubmissionId()
    {
        $id = $this->plugin->getSetting(CONTEXT_ID_NONE, 'lastSubmissionId');
        if ($id == null || $id == "") {
            return 0;
        }
        return $id;
    }

    function setLastSubmissionId($id)
    {
        $this->plugin->updateSetting(CONTEXT_ID_NONE, 'lastSubmissionId', $id, 'int');
    }


    public function getUserTransferObject($user)
    {

        if ($user == null) {
            return null;
        }

        $data = $user->getAllData();

        $data["password"] = "";

        return $data;
    }

    public function getJournalTransferObject($journal)
    {
        if ($journal == null) {
            return null;
        }

        return $journal->getAllData();
    }

    public function saveActionInfo($type, $action, $params, $payload, $userId, $contextId, $date)
    {
        /** @var ActionInfoDAO */
        $actionInfoDao =& DAORegistry::getDAO('ActionInfoDAO');
        /** @var ActionInfo */
        $actionInfo = $actionInfoDao->newDataObject();

        $actionInfo->setUserId($userId == null ? 0 : $userId);
        $actionInfo->setContextId($contextId == null ? 0 : $contextId);
        $actionInfo->setDate($date);
        $actionInfo->setType($type);
        $actionInfo->setAction($action);
        $actionInfo->setParams($params);
        $actionInfo->setPayload($payload);

        $actionInfoDao->insertObject($actionInfo);
    }
}