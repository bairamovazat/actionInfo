<?php

import('plugins.generic.actionInfo.classes.AbstractScheduledTask');


class CentralServerScheduledTask extends AbstractScheduledTask
{
    private $plugin;

    private $centralServerUrl;
    private $centralServerToken;

    public function __construct()
    {
        $this->centralServerUrl = new PluginSetting("central-server-url", "Url центрального сервера");
        $this->centralServerToken = new PluginSetting("central-server-token", "Токен для центрального сервера");
    }

    function getSettings() {
        return [
            $this->centralServerUrl,
            $this->centralServerToken,
        ];
    }

    public function executeAction($plugin)
    {
        $this->plugin = $plugin;

        $url = $this->centralServerUrl->getValue($plugin);
        $token = $this->centralServerToken->getValue($plugin);

        $actionInfoDao =& DAORegistry::getDAO('ActionInfoDAO');

        $startId = $this->getLastImportedId();
        $lastActionInfo = $actionInfoDao->getLast();

        $endId = ($lastActionInfo == null ? 0 : $lastActionInfo->getId());

        $data = $actionInfoDao->getByIdBetween($startId, $endId);

        $result = array();

        $journalDao = DAORegistry::getDAO('JournalDAO');
        /* @var $journalDao JournalDAO */
        $userDao = DAORegistry::getDAO('UserDAO');
        /* @var $userDao UserDAO */


        /** @var ActionInfo $actionInfo */
        while ($actionInfo = $data->next()) {

            $userId = $actionInfo->getUserId();
            $user = null;
            if ($userId != null && $userId != 0) {
                $user = $userDao->getById($userId);
            }

            $contextId = $actionInfo->getContextId();
            $context = null;
            if ($contextId != null && $contextId != 0) {
                $context = $journalDao->getById($contextId);
            }

            $resultElement = array(
                'actionId' => $actionInfo->getId(),
                'type' => $actionInfo->getType(),
                'action' => $actionInfo->getAction(),
                'params' => $actionInfo->getParams(),
                'payload' => $actionInfo->getPayload(),
                'date' => $actionInfo->getDate(),
                'userId' => $actionInfo->getUserId(),
                'user' => $this->getUserTransferObject($user),
                'contextId' => $actionInfo->getContextId(),
                'context' => $this->getJournalTransferObject($context),
            );

            $result[] = $resultElement;
        }


        $requestResult = $plugin->sendRequestToCentralServer($url, $result, $token);

        if ($requestResult["code"] == 201) {
            $this->setLastImportedId($endId);
        } else {
            error_log(print_r($requestResult));
        }
    }

    function getLastImportedId()
    {
        $id = $this->plugin->getSetting(CONTEXT_ID_NONE, 'lastImportedId');
        if ($id == null || $id == "") {
            return 0;
        }
        return $id;
    }

    function setLastImportedId($id)
    {
        $this->plugin->updateSetting(CONTEXT_ID_NONE, 'lastImportedId', $id, 'int');
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
}