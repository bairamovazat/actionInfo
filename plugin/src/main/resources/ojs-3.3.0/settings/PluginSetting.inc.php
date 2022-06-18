<?php

class PluginSetting
{

    private $id;
    private $description;

    public function __construct($id, $description)
    {
        $this->id = $id;
        $this->description = $description;
    }

    public function getId()
    {
        return $this->id;
    }

    public function setId($id)
    {
        $this->id = $id;
    }

    public function getDescription()
    {
        return $this->description;
    }

    public function setDescription($description)
    {
        $this->description = $description;
    }

    public function getValue($plugin)
    {
        return $plugin->getSetting(CONTEXT_ID_NONE, $this->getId());
    }

    public function setValue($plugin, $value)
    {
        $plugin->updateSetting(CONTEXT_ID_NONE, $this->getId(), $value, "string");
    }

}