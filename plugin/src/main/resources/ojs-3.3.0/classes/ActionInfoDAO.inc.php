<?php

/**
 * @file classes/StaticPagesDAO.inc.php
 *
 * Copyright (c) 2014-2020 Simon Fraser University
 * Copyright (c) 2003-2020 John Willinsky
 * Distributed under the GNU GPL v3. For full terms see the file docs/COPYING.
 *
 * @package plugins.generic.staticPages
 * @class StaticPagesDAO
 * Operations for retrieving and modifying StaticPages objects.
 */

import('lib.pkp.classes.db.DAO');
import('plugins.generic.actionInfo.classes.ActionInfo');


class ActionInfoDAO extends DAO
{

    /**
     * Get a static page by ID
     *
     * @param int $staticPageId Static page ID
     * @param int $contextId Optional context ID
     */
    public function getById($staticPageId, $contextId = null)
    {
        $params = [(int)$staticPageId];
        if ($contextId) {
            $params[] = (int)$contextId;
        }

        $result = $this->retrieve(
            'SELECT * FROM action_info WHERE id = ?'
            . ($contextId ? ' AND context_id = ?' : ''),
            $params
        );
        $row = $result->current();
        return $row ? $this->_fromRow((array)$row) : null;
    }


    public function getByIdBetween($startId, $endId, $contextId = null)
    {
        $params = [(int)$startId, (int)$endId];
        if ($contextId) {
            $params[] = (int)$contextId;
        }

        $result = $this->retrieve(
            'SELECT * from action_info where id > ? and id <= ?'
            . ($contextId ? ' AND context_id = ?' : ''),
            $params
        );
        return new DAOResultFactory($result, $this, '_fromRow');
    }

    public function getLast($contextId = null)
    {
        $params = [];
        if ($contextId) {
            $params[] = (int)$contextId;
        }

        $result = $this->retrieve(
            'SELECT * FROM action_info '
            . ($contextId ? ' where context_id = ?' : '') . 'order by id desc limit 1',
            $params
        );

        $row = $result->current();
        return $row ? $this->_fromRow((array)$row) : null;
    }

//	/**
//	 * Get a set of static pages by context ID
//	 *
//	 * @param int $contextId
//	 * @param RangeInfo $rangeInfo optional
//	 *
//	 * @return DAOResultFactory
//	 */
//	public function getByContextId($contextId, $rangeInfo = null)
//	{
//		$result = $this->retrieveRange(
//			'SELECT * FROM action_info WHERE context_id = ?',
//			[(int) $contextId],
//			$rangeInfo
//		);
//		return new DAOResultFactory($result, $this, '_fromRow');
//	}

//	/**
//	 * Get a static page by path.
//	 *
//	 * @param int $contextId Context ID
//	 * @param string $path Path
//	 *
//	 * @return StaticPage
//	 */
//	public function getByPath($contextId, $path)
//	{
//		$result = $this->retrieve(
//			'SELECT * FROM action_info WHERE context_id = ? AND path = ?',
//			[(int) $contextId, $path]
//		);
//		$row = $result->current();
//		return $row ? $this->_fromRow((array) $row) : null;
//	}

    /**
     * Insert a static page.
     *
     * @param ActionInfo $actionInfo
     *
     * @return int Inserted static page ID
     */
    public function insertObject($actionInfo)
    {
        $this->update(
            'INSERT INTO action_info (type, action, params, payload, date, user_id, context_id) VALUES (?, ?, ?, ?, ?, ?, ?)',
            [$actionInfo->getType(), $actionInfo->getAction(), $actionInfo->getParams(), $actionInfo->getPayload(),
                $actionInfo->getDate(), $actionInfo->getUserId(), $actionInfo->getContextId()]
        );

        $actionInfo->setId($this->getInsertId());

        return $actionInfo->getId();
    }

    /**
     * Update the database with a static page object
     *
     * @param ActionInfo $actionInfo
     */
    public function updateObject($actionInfo)
    {
        $this->update(
            'UPDATE	action_info
			SET	type = ?,
				 action = ?,
				 params = ?,
				 payload = ?,
				 date = ?,
				 user_id = ?,
				 context_id = ?
			WHERE id = ?',
            [
                $actionInfo->getType(),
                $actionInfo->getAction(),
                $actionInfo->getParams(),
                $actionInfo->getPayload(),
                (int)$actionInfo->getDate(),
                (int)$actionInfo->getUserId(),
                (int)$actionInfo->getContextId(),

                (int)$actionInfo->getId()
            ]
        );
    }

    /**
     * Delete a static page by ID.
     *
     * @param int $actionInfoId
     */
    public function deleteById($actionInfoId)
    {
        $this->update(
            'DELETE FROM action_info WHERE id = ?',
            [(int)$actionInfoId]
        );
    }

    /**
     * Delete a static page object.
     *
     * @param ActionInfo $actionInfo
     */
    public function deleteObject($actionInfo)
    {
        $this->deleteById($actionInfo->getId());
    }

    /**
     * Generate a new static page object.
     *
     * @return ActionInfo
     */
    public function newDataObject()
    {
        return new ActionInfo();
    }

    /**
     * Return a new static pages object from a given row.
     *
     * @return ActionInfo
     */
    public function _fromRow($row)
    {
        $staticPage = $this->newDataObject();
        $staticPage->setId($row['id']);
        $staticPage->setType($row['type']);
        $staticPage->setAction($row['action']);
        $staticPage->setParams($row['params']);
        $staticPage->setPayload($row['payload']);
        $staticPage->setDate($row['date']);
        $staticPage->setUserId($row['user_id']);
        $staticPage->setContextId($row['context_id']);

        return $staticPage;
    }

    /**
     * Get the insert ID for the last inserted static page.
     *
     * @return int
     */
    public function getInsertId()
    {
        return $this->_getInsertId('action_info', 'id');
    }

}
