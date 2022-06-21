<?php

import('plugins.generic.actionInfo.classes.AbstractHandler');
import('plugins.generic.actionInfo.settings.PluginSetting');


class LoadHandler extends AbstractHandler
{
    private $whiteFilter;
    private $blackFilter;

    public function __construct()
    {
        $this->whiteFilter = new PluginSetting("white-filter", "Белый список url разделённый запятыми");
        $this->blackFilter = new PluginSetting("black-filter", "Чёрный список url разделённый запятыми");
    }

    function getSettings() {
        return [
            $this->whiteFilter,
            $this->blackFilter,
        ];
    }

    function getHandlerName()
    {
        return "LoadHandler";
    }



    function process($hookName, $args, $plugin)
    {

        $user = $this->getUser();

        $request = Application::get()->getRequest();
        $url = $request->getRequestPath();

        $blackFilterValue = $this->blackFilter->getValue($plugin);

        if ($blackFilterValue != null && $blackFilterValue != "") {
            $str_arr = preg_split ("/\,/", $blackFilterValue);
            foreach ($str_arr as $item) {
                if (strpos($url, $item) !== false) {
                    return;
                }
            }
        }


        if (strpos($url, 'notification/fetchNotification') !== false ||
            strpos($url, 'management/settings') !== false) {
            return;
        }

        if ($user != null ) {
            $this->saveActionInfo("PAGE_LOAD", $url, $request->getQueryString(), "", time());
        }
    }

}