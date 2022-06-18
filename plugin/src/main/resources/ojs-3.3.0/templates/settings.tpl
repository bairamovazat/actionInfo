<script>
    $(function() {ldelim}
        $('#tutorialExampleSettings').pkpHandler('$.pkp.controllers.form.AjaxFormHandler');
        {rdelim});
</script>

<form
        class="pkp_form"
        id="tutorialExampleSettings"
        method="POST"
        action="{url router=$smarty.const.ROUTE_COMPONENT op="manage" category="generic" plugin=$pluginName verb="settings" save=true}"
>
    <!-- Always add the csrf token to secure your form -->
    {csrf}

    {fbvFormArea}
    {fbvFormSection}

    {foreach from=$settings key=id item=setting}
        {fbvElement
        type="text"
        id=$setting->getId()
        value=$setting->getValue($plugin)
        label=$setting->getDescription()
        }
    {/foreach}

    {/fbvFormSection}
    {/fbvFormArea}
    {fbvFormButtons submitText="common.save"}
</form>