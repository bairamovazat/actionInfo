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

class ActionInfo extends DataObject {


	function getId() {
		return $this->getData('id');
	}

	function setId($id) {
		return $this->setData('id', $id);
	}

	function getType() {
		return $this->getData('type');
	}

	function setType($id) {
		return $this->setData('type', $id);
	}

	function getAction() {
		return $this->getData('action');
	}

	function setAction($url) {
		return $this->setData('action', $url);
	}

	function getParams() {
		return $this->getData('params');
	}

	function setParams($url) {
		return $this->setData('params', $url);
	}

	function getPayload() {
		return $this->getData('payload');
	}

	function setPayload($payload) {
		return $this->setData('payload', $payload);
	}

	function getDate() {
		return $this->getData('date');
	}

	function setDate($date) {
		return $this->setData('date', $date);
	}

	function getUserId() {
		return $this->getData('user_id');
	}

	function setUserId($id) {
		return $this->setData('user_id', $id);
	}

	function getContextId() {
		return $this->getData('context_id');
	}

	function setContextId($id) {
		return $this->setData('context_id', $id);
	}

}

?>
