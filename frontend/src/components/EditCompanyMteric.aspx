<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPopup.Master" %>
<%@ OutputCache CacheProfile="Page"%>


<asp:Content ID="Content1" ContentPlaceHolderID="MasterContent" runat="server">
    <div class="pop-wrapper edit-metric-dialog" id="editCompanyMetricDialogContent" data-bind="visibility: loaded, showProgress: showProgress">
        <div class="pop-content metric-content">
            <div class="drawer-header">
    <h2 style="float: left;">
        <span data-bind="visible: IsNewCompanyMetric">Add </span>
        <span data-bind="invisible: IsNewCompanyMetric">Edit </span>
        <span data-bind=" visible: IsMetricLabelVisible">Metric</span>
    </h2>
    <div style="float: right; margin-top: -5px; margin-right: -18px; display: flex; flex-direction: row-reverse; gap: 6px;">
                <!-- Replace with this button + dropdown menu: -->
<div class="historical-values-container" style="position: relative; display: inline-block;" data-bind="visible: IsHistoricalValuesButtonVisible">
    <button class="button btn-ghost--m" style="padding: 5px 10px;" 
            title="Manage historical values for this metric"
            data-bind="click: clickHistoricalValuesButton"
            id="historicalValuesButton">
        Historical Values
    </button>
  
</div>
    </div>
</div>
            <div style="clear: both;"></div>

            <div class="simple-tabs" style="display: none; margin-bottom: 15px;">
                <button id="visualTab" class="tab-button active" style="padding: 10px 20px; background: none; border: none; cursor: pointer; font-weight: bold; color: #23a99a; border-bottom: 3px solid #23a99a;">Target</button>
                <button id="metricTab" class="tab-button" style="padding: 10px 20px; background: none; border: none; cursor: pointer; border-bottom: 3px solid transparent;">Metric</button>
            </div>

            <%--Metric Details--%>
            <div class="metric-name-info">
                <ul class="form no-style">
                <li>
                    <div class="flex half-columns flex-wrap">
                        <div class="flex-auto margin-right">
                            <div class="label">
                                Name
                            </div>
                            <div class="check-textbox middle">
                                <input type="text" 
                                    class="autocomplete-off" 
                                    placeholder="Name of the Metric" 
                                    autofocus data-bind="value: companyMetric.MetricName, events: { change: onChangeMetricName }" 
                                    maxlength="150" />
                            </div>
                        </div>
                        <div class="flex-auto">
                            <div class="label">
                                Unique Identifier
                                <span class="radoloHelpButton margin-left-less margin-top-less" 
                                    data-role="helpbutton" 
                                    data-helpname="CompanyMetricsEdit-UniqueIdentifier"></span>
                            </div>
                            <div class="check-textbox middle">
                                <input type="text" 
                                    placeholder="Enter unique identifier" 
                                    autofocus data-bind="value: companyMetric.UniqueID, events: { change: onChangeUniqueIdentifier }" 
                                    maxlength="50" />
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="flex half-columns flex-wrap">
                        <div class="flex-auto margin-right">
                            <div class="label">
                                Owner
                            </div>
                            <input id="companyMetricOwner"
                                class="input autocomplete-off inputFieldClass"
                                data-role="autocomplete"
                                data-template="personTemplate"
                                data-placeholder="Search or Invite Team Members"
                                data-header-template="InviteHeaderTemplate"
                                data-text-field="Name"
                                data-filter="contains"
                                data-bind="value: owner, source: dsSearchPersons, events: { filtering: toggleInviteInput, select: onSelectOwner, change: onChangeOwner }"
                                spellcheck="false"
                                style="width: 100%;" />
                        </div>
                        <div class="flex-auto">
                            <div class="label">
                                Status
                                <span class="radoloHelpButton margin-left-less margin-top-less" data-role="helpbutton" data-helpname="CompanyMetricsEdit-Status"></span>
                            </div>
                            <div class="check-textbox middle">
                                
                            <input class="dropdown dropdown-match-input-height" 
                                    data-role="dropdownlist" 
                                    data-value-primitive="true" 
                                    data-option-label="Select a Status"
                                    data-text-field= "text"
                                    data-value-field= "value"
                                    data-bind="value: companyMetric.Status, source:statusOptions" />
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div>
                        <div class="label">
                            Description
                        </div>                        
                            <div class="textarea limit-min-height"
                                data-role="aligneditor"
                                data-bind="value: companyMetric.Description">
                            </div>
                    </div>
                </li>
            </ul>
        </div>
            <div class="metric-value-info">
                <div>
                    <ul class="form no-style">
                        <li>
                            <div class="flex space-between flex-wrap" data-bind="css: { half-columns: CalculatedMetricInactive }">
                                <div class="flex-auto margin-right" data-bind="visible: bIsCalculatedMetricsFeatureEnabled">
                                    <div class="label">
                                        Value Source
                                    </div>
                                    <input class="dropdown dropdown-match-input-height"  data-align-feature-flag="Numbers.Web.Locked"
             data-align-feature-flag-behavior="locked"
                                        data-role="dropdownlist" 
                                        data-text-field= "text"
                                        placeholder="Select a Value Source"
                                        data-value-field= "value"
                                        data-template="visibilityTemplateCalculatedMetricsIncluded"
                                        data-bind="value: selectedIntegration,source: integrationOptions, events: { open: clickValuesourceDropdown, change: clickChangeIntegration, select: disableOptions}"/>
                                        <script id="visibilityTemplateCalculatedMetricsIncluded" type="text/x-kendo-template">
                                                <span class="#: isDisabled ? 'k-state-disabled': ''#">
                                                    #: text #
                                                </span>
                                                #if (text=="Formula" && !isDisabled) { #
                                                    <span id="formula-preview" class="company-tag preview-tag" >Preview</span>
                                                #}#
                                            </script>
                                    </div>
                                <div class="margin-right flex-auto" data-bind="visible: CalculatedMetricActive">
                                    <div class="label">
                                        Format
                                    </div>
                                    <input class="dropdown dropdown-match-input-height"
                                        data-role="dropdownlist"
                                        data-text-field="text"
                                        data-value-field="value"
                                        data-bind="value: companyMetric.FormatString, source: formatStringOptions, events: { change: onFormatStringChange }"/>
                                </div>
                                <div>
                                    <div class="label">
                                        Current Value
                                    </div>
                                    <div class="check-textbox middle">
                                        <input class="padding-top"
                                        type="text"
                                        placeholder="Enter a Value" 
                                        autofocus 
                                        data-bind="value: companyMetric.Value, disabled: isNonEditableMetricIntegration" maxlength="50" />
                                    </div>
                                </div>
                            </div>
                        </li>
                        <%--  We hide the integrations dropdown (at the bottom) and move it up when the calculated FF is on, butwe need a better solution --%>
                        <li data-bind="visible:bIsCalculatedMetricsFeatureEnabled"> 
                            <div class="metric-integration-salesforce flex border-section" data-bind="visible: SalesforceActive">
                                <div class="flex-auto input-margin width-half">
                                    <label>
                                        <div class="label">
                                            Salesforce Report
                                        </div>
                                    </label>
                                    <input class="dropdown dropdown-match-input-height"
                                            data-role="dropdownlist"
                                            data-auto-bind="false"
                                            data-text-field="text"
                                            data-value-field="value"
                                            data-bind="value: selectedSFReport, source: dsSalesforceReportList, events: { change: clickChangeReport }" />
                                </div>
                                <div class="flex-auto input-margin width-half">
                                    <label>
                                        <div class="label">
                                            Salesforce Metric
                                        </div>
                                    </label>
                                    <input class="dropdown dropdown-match-input-height"
                                            data-role="dropdownlist"
                                            data-auto-bind="false"
                                            data-text-field="text" 
                                            data-value-field="value"
                                            data-template="templateSalesforceMetricDropdown"
                                        data-header-template="templateSalesforceMetricDropdownHeader"
                                            data-bind="value: selectedSFMetric, enabled: isMetricEnabled, source: salesforceMetricList, events: { change: clickChangeMetric }" />
                                </div>
                            </div>
                            <div>
                                <label class="sub-text" data-bind="visible: ZapierActive">
                                    <em>  Zapier will use the Unique Identifier above to locate and update the Metric's Value.</em>
                                </label>
                            </div>     
                            <div class="metric-integration-hubspot flex border-section" data-bind="visible: HubSpotActive">
                                <div class="flex-auto input-margin width-half">
                                    <label>
                                        <div class="label">
                                            HubSpot Metric
                                        </div>

                                    </label>
                                    <input class="dropdown dropdown-match-input-height"
                                            data-role="dropdownlist"
                                            data-auto-bind="false"
                                            data-text-field="Name"
                                            data-value-field="ID"
                                            data-option-label="Select a HubSpot Metric"
                                            data-bind="value: hubSpot.selectedMetric, source: hubSpot.dsMetrics , events: { select: hubSpot.changeMetric }" />
                               
                                    <div data-bind="visible: hubSpot.showInput.dateRange">
                                        <label>
                                            <div class="label" data-bind="text: hubSpot.parameterControlLabel.dateRange"></div>
                                        </label>
                                        <input class="dropdown dropdown-match-input-height"
                                                data-role="dropdownlist"
                                                data-auto-bind="false"
                                                data-text-field="Label"
                                                data-value-field="ID"
                                                data-bind="value: hubSpot.args.dateRange, source: hubSpot.dsDateRangeOptions , events: { select: hubSpot.changeDateRange }" />
                                    </div>
                                    <div data-bind="visible: hubSpot.showInput.dateRangeStart">
                                        <label>
                                            <div class="label" data-bind="text: hubSpot.parameterControlLabel.dateRangeStart"></div>
                                        </label>
                                        <input  data-role="datepicker"
                                                data-bind="value: hubSpot.args.dateRangeStart, events: { change: hubSpot.changeDateRangeStart }" />
                                    </div>
                                    <div data-bind="visible: hubSpot.showInput.dateRangeEnd">
                                        <label>
                                            <div class="label" data-bind="text: hubSpot.parameterControlLabel.dateRangeEnd"></div>
                                        </label>
                                        <input data-role="datepicker"
                                                data-bind="value: hubSpot.args.dateRangeEnd, events: { change: hubSpot.changeDateRangeEnd }" />
                                    </div>
                                </div>

                                <div class="flex-auto input-margin width-half">
                                    <div data-bind="visible: hubSpot.showInput.pipelineID">
                                        <label>
                                            <div class="label" data-bind="text: hubSpot.parameterControlLabel.pipelineID"></div>
                                        </label>
                                        <input class="dropdown dropdown-match-input-height"
                                            data-role="dropdownlist"
                                            data-auto-bind="false"
                                            data-text-field="Label"
                                            data-value-field="ID"
                                            data-bind="value: hubSpot.args.pipelineID, source: hubSpot.dsPipelines , events: { select: hubSpot.changePipeline }" />
                                    </div>    
                                    
                                    <div data-bind="visible: hubSpot.showInput.stageID">
                                        <label>
                                            <div class="label" data-bind="text: hubSpot.parameterControlLabel.stageID"></div>
                                        </label>
                                        <input class="dropdown dropdown-match-input-height"
                                            data-role="dropdownlist"
                                            data-auto-bind="false"
                                            data-text-field="Label"
                                            data-value-field="ID"
                                            data-bind="value: hubSpot.args.stageID, source: hubSpot.dsStages , events: { select: hubSpot.changeStage }" />
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>              
            </div>

           
          <%--  <div class="flex-auto md-col-6 margin-bottom">
    <div class="label" style="display: flex; align-items: center; gap: 10px;" data-bind="visible:isConnectionsVisible">
        Connections 
        <div class="metric-connections center-self-vertically padding-left">
            <div class="link padding-left-more center" 
                 title="Click to manage this Metric's Connections" 
                 data-bind="text: ConnectedObjects.length, click: clickManageCompanyMetricConnections">0
            </div>
        </div>
    </div>
</div>--%>

            <%--Calculated Metrics--%>
            <div class="metric-calculated-info border-section margin-bottom padding-less" data-bind="visible:CalculatedMetricActive" style="display: none;">
                <div class="flex flex-wrap">
                    <div class="flex-auto margin-half">
                        <div class="label">
                            Formula Builder
                            <span class="radoloHelpButton margin-left-less margin-top-less" data-role="helpbutton" data-helpname="CompanyMetricsEdit-FormulaBuilder"></span>
                        </div>
                        <div class="check-textbox middle">
                            <div class="textarea"
                                id="formula-builder"
                                data-role="aligneditor"
                                data-paste-styles="true"
                                data-default-text='<span style="opacity: 0.5;"><span> </span>(<span> </span><span contenteditable="false" class="metric-pill small" style="border-radius: 2em;">Metric</span><span> </span>+<span> </span><span contenteditable="false" class="metric-pill small" style="border-radius: 2em;">Metric</span><span> </span>)<span> </span>/<span> </span><span>100</span><span> </span></span>'>
                            </div>
                            <div class="calculated-actions">
                                <span data-role="tooltip" title="Clear">
                                    <span 
                                        class="icon ion-android-cancel margin-left-less red" 
                                        data-bind="events: { click: calculated.clearFormula }"
                                        style="font-size: 2em;">
                                    </span>
                                </span>
                                <span data-role="tooltip" title="Validate and Calculate">
                                    <span 
                                        class="icon ion-checkmark-circled margin-left-less green" 
                                        data-bind="events: { click: calculated.validateFormula }"
                                        style="font-size: 2em;">
                                    </span>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="flex flex-row third-columns">
                    <div class="metric-card">
                        <div class="padding no-padding-bottom" style="width: 75%">
                        <div class="label">
                            Search Metric
                        </div>
                        <div class="check-textbox middle">
                            <input type="text" placeholder="Name or Owner" data-bind="events: { keyup: calculated.onFilterChange }"/>
                        </div>
                        </div>
                        <div class="metric-selection card-list-wrapper"
                            data-role="listview"
                            data-auto-bind="false"
                            data-bind="source: calculated.dsMetrics"
                            data-template="templateMetricList"
                            style="margin-top: 15px;">
                        </div>
                    </div>
                    <div>
                         <div class="flex rowReverse margin-half">
                            <div class="button calculator-btn margin-left-10" data-bind="events: { click: calculated.insertOperator }">
                                /
                            </div>
                            <div class="button calculator-btn margin-left-10" data-bind="events: { click: calculated.insertOperator }">
                                -
                            </div>
                            <div class="button calculator-btn margin-left-10" data-bind="events: { click: calculated.insertOperator }">
                                +
                            </div>
                        </div>
                        <div class="flex rowReverse margin-half">
                            <div class="button calculator-btn margin-left-10" data-bind="events: { click: calculated.insertOperator }">
                                )
                            </div>
                            <div class="button calculator-btn margin-left-10" data-bind="events: { click: calculated.insertOperator }">
                                (
                            </div>
                            <div class="button calculator-btn margin-left-10" data-bind="events: { click: calculated.insertOperator }">
                                *
                            </div>
                         </div>                     
                       <!-- 
                        <div class="flex rowReverse">
                            <button class="button btn-filled small margin-bottom" data-bind="disabled: calculated.invalid, events: { click: calculated.calculateValue }">
                                Calculate Value
                            </button>
                        </div> -->
                    </div>
                </div>
            </div> 

            <!-- New Readonly Fields for Visual Tab -->
<div class="metric-readonly-info" data-bind="visible: isVisualTab" style="margin-bottom: 10px;">
    <div class="flex half-columns flex-wrap" style="gap: 15px;"> <!-- Added gap between inputs -->
        <!-- Readonly Name -->
        <div class="flex-auto md-col-6 margin-bottom">
            <div class="label" style="margin-bottom:5px">Metric Name</div>
            <div style="background-color: #e6e6e6; padding: 8px 12px; border-radius: 5px;  min-height: 38px; display: flex; align-items: center;">
                <span data-bind="text: companyMetric.MetricName" style="color: #5a6570;"></span>
            </div>
        </div>

        <!-- Readonly Owner -->
        <div class="flex-auto md-col-6 margin-bottom">
            <div class="label" style="margin-bottom:5px">Metric Owner</div>
            <div style="background-color: #e6e6e6; padding: 8px 12px; border-radius: 5px;  min-height: 38px; display: flex; align-items: center;">
                <span data-bind="text: companyMetric.Owner.Name" style="color: #5a6570;"></span>
            </div>
        </div>
    </div>
</div>


            <%--KPI Targets--%>
            <%--<div class="metric-target-info margin-bottom border-section" data-bind="visible:metricTargetsFeatureEnabledAndIsKPIComponent">--%>
            <div class="metric-target-info margin-bottom " data-bind="visible:metricTargetsFeatureEnabledAndIsKPIComponent">
            
               <%--Owner Section - Edited on: March 11, 2025 by Vikas--%>
               <div data-bind="visible:targetOwnerInfo">
   
                   <div class="owner-info-container" data-bind="visible:IsTargetownercontainerVisible">
    <!-- Target Owner Label -->
                       
    <div class="label" style="margin-bottom: 5px;margin-top: -10px; color: #3A4B59;
    font-weight: 700;
    font-size: 12px;">
        Target Owner
    </div>

    <!-- Flex container for input and indicators -->
    <div style="display: flex; align-items: center; gap: 8px;">
       

        <div style="flex-grow: 1;">
            <input id="companyMetricKPIOwner"
                class="input autocomplete-off inputFieldClass"
                data-role="autocomplete"
                data-template="personTemplate"
                data-placeholder="Search Targets..."
                data-header-template="InviteHeaderTemplate"
                data-text-field="Name"
                data-filter="contains"
                data-bind="value: targetOwner, source: dsSearchPersons, events: { filtering: toggleInviteInput, select: onSelectTargetOwner, change: onChangeTargetOwner },enabled: IsTargetOwnerEnabled"
                spellcheck="false"
                style="width: 100%; " />
        </div>



        <!-- Link icon and count (visible only when IsTargetLinked is true) -->
        <div data-bind="visible:IsTargetLinked" style="display: flex; align-items: center; white-space: nowrap;">
            <span class="icon ico-link" title="Target Linked" style="font-size: 1.2em;"</span>
            <span title="Connections" data-bind="text: companyMetric.SharedTarget.TargetLinkedCount" style="
                background-color: #d4edda; 
                color: #000;
                padding: 2px 8px;
                border-radius: 5px;
                font-size: 14px;
                font-weight: 500;
                
                cursor: pointer;"></span>
        </div>
    </div>
</div>


</div>
                

                <div class="flex space-between">
                    
                    <div class="input-margin width-half">
                        <ul class="no-style">
                            <div class="label" style="display: flex; align-items: center;">
                                <span>Target Options</span>
                                 <button type="button" data-bind="click:showCopySelectedTargetModal , visible:isBrokenLinkIconVisible"
        style="background: none; border: none; color: black; font-size: 16px; cursor: pointer;margin-left: -5px;"
        title="Unlink Existing Target">
    <i class="icon ico-link-broken"></i>
</button>
                            </div>
                            <li class="padding-top">
                                <div data-bind="visible:IsSharedTargetRadioButtonVisible">
                                    <div class="radio"><input type="radio" data-role="uniform" name="#= companyMetric.ID #" value="SharedTarget" data-bind="value: targetTemplateOptions,disabled: disableTargetOptions, events: { change: clickChangeTargetTemplate }" /></div>
                                    <label class="label">Existing Target</label> 
                                    <span data-bind="visible: selectedSharedTargetName" style="margin-left: -10px;">
                                        
                                       
                                       <%-- <button type="button" data-bind="click:showCopySelectedTargetModal , visible:isBrokenLinkIconVisible"
        style="background: none; border: none; color: black; font-size: 16px; cursor: pointer;"
        title="Clear selected target">
    <i class="icon ico-link-broken"></i>
</button>--%>
                                      <br />  <a href="#" id="showSharedTargetTrigger" title="Click to view selected shared targets" style="text-decoration: none;">
  <strong data-bind="text: selectedSharedTargetName"
          style="padding: 2px 6px; border-radius: 5px; margin-left: 25px; display: inline-block; cursor: pointer;">
  </strong>
</a>

                                    </span>
                                     
                                </div>
                                
                               
                                   
                            </li>
                            
                            <li>
                                <div class="radio">
                                    <input type="radio" data-role="uniform" name="#= companyMetric.ID #" value="Custom" data-bind="value: targetTemplateOptions, disabled: disableTargetOptions, events: { change: clickChangeTargetTemplate }" />
                                </div>
                                <label class="label">Custom</label>
                            </li>
                            <li>
                                <div class="radio">
                                    <input type="radio" data-role="uniform" name="#= companyMetric.ID #" value="TimeBased" data-bind="value: targetTemplateOptions, disabled: disableTargetOptions, events: { change: clickChangeTargetTemplate }" />
                                </div>
                                <label class="label">Time-Based</label>
                            </li>
                            <li >
                                <div class="radio">
                                    <input type="radio" data-role="uniform" name="#= companyMetric.ID #" value="None" data-bind="value: targetTemplateOptions,disabled: disableTargetOptions, events: { change: clickChangeTargetTemplate }" />
                                </div>
                                <label class="label">None</label>
                            </li>
                           
                            
                        </ul>
                        <%--added by vikas on 03/21/2025--%>
                        <%--<button id="copyButton" class="button btn-ghost--m" title="Copy Target" data-bind="visible: IsCopyButtonVisible" style="margin-top:10px">
                    Copy Target
                </button>--%>
                          
                    </div>
                    <div class="input-margin width-half" data-bind="invisible: shouldShowTargetOptions">
                       
                        <div data-bind="visible: showMetricTargetCustomOption">
                            <ul class="custom-targets-list" style="margin-top: 10px">
                                <div class="label margin-bottom">Targets</div>
                                     <li class="dark-green" >
                                         <input class="input" placeholder="400" data-bind="value:companyMetric.KPITarget.Level1" type="text" maxlength="50" style="width: 100%;"/>
                                     </li>
                                     <li class="light-green" >
                                         <input class="input" placeholder="300" data-bind="value:companyMetric.KPITarget.Level2" type="text" maxlength="50" style="width: 100%;" />
                                     </li>
                                     <li class="yellow" >
                                         <input class="input" placeholder="200" data-bind="value:companyMetric.KPITarget.Level3" type="text" maxlength="50" style="width: 100%;"/>
                                     </li>
                                     <li class="red" >
                                         <input class="input" placeholder="100" data-bind="value:companyMetric.KPITarget.Level4" type="text" maxlength="50" style="width: 100%;" />
                                     </li>
                           </ul>
                    </div>

                    <div data-bind="invisible: showMetricTargetCustomOption">
                        <ul class="no-style">
                            <li>
                                <div class="flex-auto">
                                    <div class="label">Start</div>
                                    <div class="check-textbox middle">
                                        <input class="input" data-bind="value:companyMetric.KPITarget.Start" type="number" />
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="flex-auto">
                                    <div class="label">Target</div>
                                    <div class="check-textbox middle">
                                        <input class="input" data-bind="value:companyMetric.KPITarget.Target" type="number" />
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <!-- Reset Button (Initially Hidden) -->
                    <div id="resetCopiedTarget" style="display: none; margin-top: 10px;">
                        <button onclick="resetCopiedTarget()" style="background-color: #f44336; color: white; border: none; padding: 5px 10px; border-radius: 5px; cursor: pointer;">
                            Reset to Original Target
                        </button>
                    </div>
                        
                            </div>

                    
                        </div>
                
                <!-- Updated HTML structure with improved styling -->
<div class="shared-targets-container" data-bind="visible: showSharedTargetOption">
    <label class="shared-targets-label" title="View all current Metrics with Targets" style="cursor:pointer">Existing Targets</label>
    <div class="input-wrapper" style="position: relative; width: 100%;">
        <!-- This div will appear over the input when visible -->
        <div id="selectedTargetDisplay" data-bind="visible:selectedTargetDisplayVisible"
             
             style="position: absolute; top: 10px; left: 0px; right: 0px; z-index: 2; display: flex; align-items: center; cursor: pointer;width: 95%;">
            
        </div>

        <input id="sharedTargetSearch"
               style="width: 100%; padding: 8px; padding-right: 30px; border-radius: 4px; border: 1px solid #ddd; z-index: 1;width: 100%;height:50px" />

        <button class="dropdown-toggle-btn" style="position: absolute; right: 0; top: 0; height: 100%; border: none; background: transparent; cursor: pointer; padding: 0px 0px 4px 10px;">
            <i class="fas fa-chevron-down"></i>
        </button>
       

    </div>
    <div class="targets-list-container" style="display: none;">
        <div class="targets-list" id="sharedTargetsList">
            <!-- Items will be populated here -->
        </div>
    </div>
</div>




                <div id="selectedTargetInfo" data-bind="visible:IsSelectedTargetInfoVisible" style="margin-top: 10px;margin-bottom: 10px;"></div>
                    </div>




<%--   <div id="CopyTargetWarningModel" class="modal" data-bind="visible: IsCopyTargetWarningModelVisible">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Copy Target</h2>
        </div>
        <div class="modal-body">
            <p>Are you sure? Do you want to copy the targets?</p>
        </div>
        <div class="modal-footer">
            <button class="k-button k-primary" id="CopyTargetOkButton">OK</button>
            <button class="k-button" id="CopyTargetCancelButton">Cancel</button>
        </div>
    </div>
</div>--%>




            <!-- Container for Selected Target Info & "Make Sharable" Checkbox -->
            <div id="sharedTargetBoxContainer" data-bind="visible:isSharedTargetBoxContainerVisible" style="margin-bottom:10px; display: none;">
                <!-- Checkbox for Making the Target Sharable -->
                <div data-bind="visible:makeTargetSharableContainerVisible">
                    <div id="makeTargetSharableContainer"  style="display: none; display: flex; align-items: center; gap: 5px;margin-left: -5px;margin-top: -5px;">
                   <input type="checkbox" id="makeTargetSharable" data-bind="checked: companyMetric.KPITarget.IsPublic, disabled: isPublicDisabled">
                    <label for="makeTargetSharable" title="Public Targets can be used by other people" style="    color: #3A4B59; font-weight: 700; font-size: 12px; cursor: pointer;">Public</label>
                    
                </div>
                </div>
                
            </div>
            
                <label style="margin-top:10px; margin-bottom: 5px;" data-bind="visible: IsTargetCommentsVisible">
                                <div class="label" style="margin-bottom: 5px;">
                                    Target Comments
                                </div>
                                <div class="textarea"
                                    data-role="aligneditor"
                                    data-bind="value: companyMetric.KPITarget.Comments" style="font-size: 12px;">
                                </div>
                            </label>
            <%--<div  data-bind="visible: IsTargetLogsVisible">
                                <div style="margin-top:10px; margin-bottom: 5px;" class="label">
                                    Change Logs
                                </div>--%>
            <div data-bind="visible: IsTargetLogsVisible" >
         <div id="toggleLogBtn"  class="log-toggle-header">
  <span class="label-text">Change Logs</span>
             <span 
        id="logCountBadge"
        title="Log Count"
                 data-bind="text:logCount"
        style="
            background-color: #d4edda; 
            color: #000000;
            padding: 2px 6px;
            border-radius: 5px;
            font-size: 12px;
            
            ">
        <strong></strong>
    </span>
  <span id="toggleArrow" class="k-icon k-i-arrow-60-down"></span>
  <div class="line"></div>
</div>

<div class="log-container" id="logContainer" style="display: none;">
  <div class="table-responsive">
    <table class="table table-bordered">
      <thead>
        <tr>
          <th>Date</th>
          <th>Modified By</th>
          <th>Action Type</th>
          <th class="change-details-col">Change Details</th>
        </tr>
      </thead>
      <tbody id="logListContainer">
        <!-- Injected rows -->
      </tbody>
    </table>
  </div>
</div>

            </div>


<!-- /ko -->


            
            
            <%--<div id="selectedTargetInfo" style="margin-top: 10px;margin-bottom: 10px;"></div>--%>
           

            <%--Metric Cadences--%>
            <div class="metric-cadence-info flex border-section" data-bind="visible:showCadenceOptions">
                <div class="input-margin" data-bind="visible: showCadenceOptions">
                    <div class="label">
                        Cadence
                        <span class="radoloHelpButton margin-left-less margin-top-less" data-role="helpbutton" data-helpname="CompanyMetricsEdit-Cadence"></span>
                    </div>
                    <input class="dropdown dropdown-match-input-height padding-top" 
                        data-role="dropdownlist" 
                        data-text-field= "text"
                        data-value-field= "value"
                        data-bind="value: selectedCadence, source:cadenceChoiceOptions, enabled:cadenceIsNonEditable, events: { change: clickChangeCadence }" />
                </div>
                <div class="flex-grow input-margin">
                    <div data-bind="visible: cadenceIsEditable">
                        <div class="label">
                            Resets On
                        </div>
                        <div>
                            <input class="dropdown dropdown-match-input-height padding-top"
                                data-role="dropdownlist"
                                data-auto-bind="false"
                                data-text-field="text" 
                                data-value-field="value" 
                                data-bind="value: selectedResetWeekDay, source:weeklyCadenceOptions, enabled:cadenceSelectionEnabled, invisible:monthlyCadenceSelected, events: { change: clickChangeResetWeekDay }"/>
                            <input class="dropdown dropdown-match-input-height padding-top"
                                data-role="dropdownlist"
                                data-auto-bind="false"
                                data-text-field="text" 
                                data-value-field="value" 
                                data-bind="value: selectedResetDayOfTheMonth, source:monthlyCadenceOptions, enabled:cadenceSelectionEnabled, visible:monthlyCadenceSelected, events: { change: clickChangeResetDayOfTheMonth }"/>
                        </div>
                    </div>
                </div>
                <div class="input-margin">
                    <div data-bind="visible: cadenceIsEditable">
                        <div class="label">
                            Reset Value
                        </div>
                        <div class="padding-top">
                            <input 
                                type="text"
                                placeholder="Enter a Value" 
                                data-bind="value: companyMetric.ResetValue" maxlength="50" />
                        </div>
                    </div>
                </div> 
            </div>
            <%--Metric Visibility-Not in Use 2/2/23--%>
            <%-- <div class="padding-right" style="display: none;">
            2021-01-13 RJK: While hiding Update Frequency and Modifiable By, set the max width to 30%                           
                <div class="label">
                    Update Frequency
                </div>
                <select class="dropdown" 
                        data-role="dropdownlist" 
                        data-value-primitive="true" 
                        data-option-label="How often should the value be updated?"
                        data-bind="value: companyMetric.UpdateFrequency">
                    <option>Daily (Weekdays)</option>
                    <option>Daily (Everyday)</option>
                    <option>Weekly</option>
                    <option>Semi-monthly</option>
                    <option>Monthly</option>
                    <option>Bi-monthly</option>
                    <option>Quarterly</option>
                </select>
            </div>
            <div class="padding-right" style="display: none;">
                <div class="label">
                    Modifiable By
                </div>
                <select class="dropdown" 
                        data-role="dropdownlist" 
                        data-value-primitive="true" 
                        data-option-label="Select who can modify this Metric..."
                        data-bind="value: companyMetric.ModifiableBy">
                    <option>Everyone</option>
                    <option>Owner</option>
                </select>
            </div>--%>
            <%--Metric Integrations (Hidden when Calculated FF is on) --%>
            <%--  We hide the integrations dropdown (at the bottom) and move it up when the alculated FF is on, we need a better solution --%>
            <div class="metric-integration-details">
                <div class="metric-integration-options">    
                    <ul class="form-normal no-style" data-bind="invisible: bIsCalculatedMetricsFeatureEnabled">
                        <li>
                            <div class="space-between">
                                <label data-bind="visible: bIsIntegrationFeatureEnabled">
                                    <div class="label">
                                        Integration
                                    </div>
                                    <input class="dropdown dropdown-match-input-height" 
                                        data-role="dropdownlist" 
                                        data-text-field= "text"
                                        data-value-field= "value"
                                        data-template="visibilityTemplateCompanyMetrics"
                                        data-bind="value: selectedIntegration,enabled: isIntegrationSelectionEnabled, source: integrationOptions, events: { change: clickChangeIntegration, select: disableOptions }"   />
                                        <script id="visibilityTemplateCompanyMetrics" type="text/x-kendo-template">
                                                    <span class="#: isDisabled ? 'k-state-disabled': ''#">
                                                       #: text #
                                                    </span>
                                                </script>
                                    </label>
                            </div>
                        </li>
                        <li> 
                            <div class="metric-integration-salesforce flex border-section" data-bind="visible: SalesforceActive">
                                <div class="flex-auto margin-half">
                                    <label>
                                        <div class="label">
                                            Salesforce Report
                                        </div>
                                    </label>
                                    <input class="dropdown dropdown-match-input-height"
                                            data-role="dropdownlist"
                                            data-auto-bind="false"
                                            data-text-field="text"
                                            data-value-field="value"
                                            data-bind="value: selectedSFReport, source: dsSalesforceReportList, events: { change: clickChangeReport }" />
                                </div>
                                <div class="flex-auto margin-half">
                                    <label>
                                        <div class="label">
                                            Salesforce Metric
                                        </div>
                                    </label>
                                    <input class="dropdown dropdown-match-input-height"
                                            data-role="dropdownlist"
                                            data-auto-bind="false"
                                            data-text-field="text" 
                                            data-value-field="value"
                                            data-template="templateSalesforceMetricDropdown"
                                            data-bind="value: selectedSFMetric, enabled: isMetricEnabled, source: salesforceMetricList, events: { change: clickChangeMetric }" />
                                </div>
                            </div>
                            <div>                              
                                <label class="sub-text" data-bind="visible: ZapierActive">
                                    <em>  Zapier will use the Unique Identifier above to locate and update the Metric's Value.</em>
                                </label>
                            </div>     
                            <div class="metric-integration-hubspot flex border-section" data-bind="visible: HubSpotActive">
                                <div class="flex-auto margin-half">
                                    <label>
                                        <div class="label">
                                            HubSpot Metric
                                        </div>
                                    </label>

                                    <input class="dropdown dropdown-match-input-height"
                                            data-role="dropdownlist"
                                            data-auto-bind="false"
                                            data-text-field="Name"
                                            data-value-field="ID"
                                            data-option-label="Select a HubSpot Metric"
                                            data-bind="value: hubSpot.selectedMetric, source: hubSpot.dsMetrics , events: { select: hubSpot.changeMetric }" />
                               

                                    <div data-bind="visible: hubSpot.showInput.dateRange">
                                        <label>
                                            <div class="label" data-bind="text: hubSpot.parameterControlLabel.dateRange"></div>
                                        </label>
                                        <input class="dropdown dropdown-match-input-height"
                                                data-role="dropdownlist"
                                                data-auto-bind="false"
                                                data-text-field="Label"
                                                data-value-field="ID"
                                                data-bind="value: hubSpot.args.dateRange, source: hubSpot.dsDateRangeOptions , events: { select: hubSpot.changeDateRange }" />
                                    </div>


                                    <div data-bind="visible: hubSpot.showInput.dateRangeStart">
                                        <label>
                                            <div class="label" data-bind="text: hubSpot.parameterControlLabel.dateRangeStart"></div>
                                        </label>
                                        <input  data-role="datepicker"
                                                data-bind="value: hubSpot.args.dateRangeStart, events: { change: hubSpot.changeDateRangeStart }" />
                                    </div>


                                    <div data-bind="visible: hubSpot.showInput.dateRangeEnd">
                                        <label>
                                            <div class="label" data-bind="text: hubSpot.parameterControlLabel.dateRangeEnd"></div>
                                        </label>
                                        <input data-role="datepicker"
                                                data-bind="value: hubSpot.args.dateRangeEnd, events: { change: hubSpot.changeDateRangeEnd }" />
                                    </div>

                                </div>
                                <div class="flex-auto  margin-half">
                                    <div data-bind="visible: hubSpot.showInput.pipelineID">
                                        <label>
                                            <div class="label" data-bind="text: hubSpot.parameterControlLabel.pipelineID"></div>
                                        </label>
                                        <input class="dropdown dropdown-match-input-height"
                                            data-role="dropdownlist"
                                            data-auto-bind="false"
                                            data-text-field="Label"
                                            data-value-field="ID"
                                            data-bind="value: hubSpot.args.pipelineID, source: hubSpot.dsPipelines , events: { select: hubSpot.changePipeline }" />
                                    </div>    
                                    
                                    <div data-bind="visible: hubSpot.showInput.stageID">
                                        <label>
                                            <div class="label" data-bind="text: hubSpot.parameterControlLabel.stageID"></div>
                                        </label>
                                        <input class="dropdown dropdown-match-input-height"
                                            data-role="dropdownlist"
                                            data-auto-bind="false"
                                            data-text-field="Label"
                                            data-value-field="ID"
                                            data-bind="value: hubSpot.args.stageID, source: hubSpot.dsStages , events: { select: hubSpot.changeStage }" />
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                    <%--  <!--VISIBILITY / TEAMS-->
                    <li data-bind="visible: bIsTeamsFeatureEnabled">
                        <div class="module padded-wrapper">
                            <ul class="module-inner-less-padding no-style">
                                <li>
                                    <label>
                                        <div class="label">
                                            Visibility
                                        </div>
                                        <input class="dropdown dropdown-match-input-height"
                                            id="visibilityDropdown"
                                            data-role="dropdownlist"
                                            data-auto-bind="false"
                                            data-template="visibilityTemplate"
                                            data-text-field= "text",
                                            data-value-field= "value",
                                            data-bind="value: selectedVisibility, source: getVisibilityOptions, events: { change: clickChangeVisibility, select: disableOptions }, disabled: isTaskSecurityDisabled"/>
                                            <script id="visibilityTemplate" type="text/x-kendo-template">
                                                <span class="#: isDisabled ? 'k-state-disabled': ''#">
                                                    #: text #
                                                </span>
                                            </script>
                                    </label>
                                </li>
                                <li data-bind="visible: isTeamsVisible">
                                    <p>
                                        <em>Select one or more teams that will have access to this Metric.
                                        </em>
                                    </p>
                                    <label>
                                        <div class="label">
                                            Teams
                                        </div>
                                        <select
                                            id="multiTeamFilter"
                                            data-role="multiselect"
                                            data-auto-bind="false"
                                            data-placeholder="Team Name"
                                            multiple="multiple"
                                            data-filter="contains"
                                            data-text-field="DisplayName"
                                            data-value-field="ID"
                                            data-bind="value: selectedTeams, source: dsUserTeams, events: { change: onChangeTeam }">
                                        </select>
                                    </label>
                                </li>
                                <li data-bind="visible: isUsersVisible">
                                    <!--<h3 class="no-margin margin-bottom-less">Priority Security
                                    </h3>-->
                                    <p>
                                        <em>Select the users who will have access to this Metric. (The owner will always have access.)
                                        </em>
                                    </p>
                                    <div class="subnav">
                                        <button class="button green" data-bind="click: clickAddAllMembers">
                                            <span class="icon ico-plus-circle"></span>
                                            Add All
                                        </button>
                                        <button class="button red" data-bind="click: clickRemoveAllMembers">
                                            <span class="icon ion-android-cancel"></span>
                                            Remove All
                                        </button>
                                    </div>
                                    <div class="margin-top-more">
                                        <div class="flex half-columns">
                                            <div class="module padded-wrapper" style="margin: 0 5px 0 0;">
                                                <h3>Give Users Access
                                                </h3>
                                                <div class="form full margin-top">
                                                    <label>
                                                        <div class="label">
                                                            Search Member
                                                        </div>
                                                        <input class="input" placeholder="Member Name..." data-bind="events: { keyup: onFilterMembersKeyup }" />
                                                    </label>
                                                </div>
                                                <ul class="card-list-wrapper user-list scroll" data-role="listview" data-auto-bind="false" data-bind="source: dsCompanyPersonsAvailableForSecureTasks" data-template="templateApprovedUsersAvailable">
                                                </ul>
                                            </div>

                                            <div class="module padded-wrapper green" style="margin: 0 0 0 5px;">
                                                <h3 class="no-margin-bottom">Users With Access
                                                </h3>
                                                <h5>
                                                    <span data-bind="text: companyMetric.ApprovedUsers.length"></span> users selected
                                                </h5>
                                                <ul class="card-list-wrapper user-list scroll" data-role="listview" data-bind="source: companyMetric.ApprovedUsers" data-template="templateApprovedUsersSelected">
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </li>--%>
                <div>
                    <ul class="form no-style">
                        <li>
                            <label>
                                <div class="label">
                                    KPI Unit
                                </div>
                                <input class="dropdown kpiUnit"
                                    data-role="dropdownlist"
                                    data-option-label="Select a KPI"
                                    data-auto-bind="false"
                                    data-template="kpiTemplate"
                                    data-header-template="kpiHeaderTemplate"
                                    data-value-template="kpiValueTemplate"
                                    data-text-field="KpiName"
                                    data-value-field="KpiID"
                                    data-height="250"
                                    data-bind="value: selectedKPI, source: dsCompanyKpis, events: { change: changeSelectedKPI }"/>
                            </label>
                        </li>
                        <li>
                            <label>
                                <div class="label">
                                    Comments
                                </div>
                                <div class="textarea"
                                    data-role="aligneditor"
                                    data-bind="value: companyMetric.Comments">
                                </div>
                            </label>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
         <%--Drawer Action Buttons--%>
       <div class="metric-actions button-footer">
  <!-- Flex container for Save and Cancel -->
  <div class="button-tray" style="display: flex; align-items: center; gap: 10px; flex-wrap: wrap;">
    <!-- Save Button (Only Visible If Authorized) -->
    <button id="saveButton" class="button green"
      data-bind="click: clickSaveCompanyMetric, enabled: IsSaveButtonVisible">
      <span class="icon ion-checkmark-circled"></span>
      <span>Save</span>
    </button>

    <!-- Cancel Button -->
    <button class="cancel" data-bind="click: clickCloseDialog">
      Cancel
    </button>
  </div>

  <%-- 
  <div style="display: flex; align-items: center; gap: 10px; margin-top: 10px; flex-wrap: wrap;">
    <!-- Message Text (Appears First) -->
    <p data-bind="visible: IsTargetLinkedMessageVisible, text: SaveButtonMessage" 
        style="color: rgb(35, 169, 154); font-weight: bold; margin: 0;">
        This target is linked. You cannot edit it unless you are the owner or an administrator.
        Do you want to make a copy of this target? Click on the copy target button above.
        <br />
    </p>
  </div>
  --%>
</div>

   

    <script id="templateApprovedUsersAvailable" type="text/html">
        <li class="card padded-wrapper hover hover-green" data-bind="click:clickSelectMember">
            <div>
                <span class="icon ico-plus-circle green"></span>
                <span data-bind="text:Name"></span>
            </div>
        </li>
    </script>
    <script id="templateApprovedUsersSelected" type="text/html">
        <li class="card padded-wrapper hover hover-red" >
            <div>
                <span class="icon ico-trash- red" data-bind="click:clickRemoveSelectedMember"></span>
                <span data-bind="text:Name"></span>
            </div>
        </li>
    </script>
    <script id="personListTemplate" type="text/html">
        <li>
            <button data-bind="click:clickRemovePerson">
                <span class="icon ion-close-circled"></span>
                <span data-bind='html:Name'></span>
            </button>
        </li>
    </script>
    <script id="templateSalesforceMetricDropdown" type="text/html">
        <div class="flex space-between" style="width:100%">
            #var salesforceMetric = data #
            <span class="ellipsis-text" style="width: 60%">#=salesforceMetric.text#</span> 
            # if (salesforceMetric.text != "None") { #
                <span class="center" style="width: 40%;"><em>#=salesforceMetric.value#</em></span>
            # } else { #
                <span class="center" style="width: 40%;"></span>
            # } #
        </div>
    </script>
    <script id="templateMetricList" type="text/html">
        <%--        TODO: move to style--%>
        <div class="card hover hover-green kpi-selection-card" style="height: 3em; width: 95%" data-bind="events: {click: calculated.insertOperand}">   
            <div class="kpi-container flex center">
                <span class="action-icon">
                    <span class="icon ico-plus-circle green"></span>
                </span>
                #if (Owner != null && Owner.Name != null && Owner.Name != "" ) {#
                <span class="profile-container padding-left"> 
                    <span> 
                        <img class="profile-pic-small vertical-align-middle" src="#=Owner.Picture#&width=34&height=34" title="#=Owner.Name#" data-role="tooltip" data-position="top" loading="lazy" />
                    </span>
                #} else {#
                <span class="profile-container padding-left" style="height: 30px;" title="No Owner" data-role="tooltip" data-position="top" >
                    <span></span>
                #}#
                </span>
                <span class="card-name-small">
                # if (Description != "" && Description != null ) { #
                    <span class="ellipsis-text" data-bind="text:MetricName" title="#: Description #" data-role="tooltip" data-position="top"></span>
                # } else {#
                    <span class="ellipsis-text" data-bind="text:MetricName"></span>
                # } #               
                </span>
                # if ( Status === 'Deprecated') { #
                <span class="card-status float-right deprecated-tag" style="width:60px;">Deprecated</span>
                #} else if ( Status === 'Inactive') { #
                <span class="card-status float-right inactive-tag" style="width:60px;">Inactive</span>
                #} else if ( Status === 'Draft') { #
                    <span class="card-status float-right draft-tag" style="width:60px;">Draft</span>
                #}#
                <span class="icons-container float-right">           
                    # if (ActiveIntegrationName === "HubSpot") { #
                    <span class="icon">
                        <span class="icon background integration-icon"
                            style="background-image: url(/img/hubspot-sprocket-orange.svg);"
                            title="HubSpot Enabled"
                            data-role="tooltip"
                            data-auto-hide="true"
                            data-position="top"></span>
                    </span>
                    # } else if (ActiveIntegrationName === "Salesforce") {#
                        # var attributes = Integrations[0] ? Integrations[0].Attributes : []; #
                        # var salesforceReportName = Align.Common.getAlignIntegrationAttributeValue('SalesforceReport', attributes) #
                    <span class="icon">
                        <span class="icon background integration-icon-salesforce"
                            style="background-image: url(/img/salesforce-logo.svg);"
                            title="Salesforce Enabled<br />#=salesforceReportName#"
                            data-role="tooltip"
                            data-auto-hide="true"
                            data-position="top">
                        </span>
                    </span>
                    # } else if (ActiveIntegrationName === "Zapier") {#
                    <span class="icon">
                        <span class="icon background integration-icon-zapier"
                            style="background-image: url(/img/zapier-logo.png);"
                            title="Zapier Enabled"
                            data-role="tooltip"
                            data-auto-hide="true"
                            data-position="top"> </span>
                        </span>
                    </span>
                    # } else { #
                        <%--No integration, Manually Updated--%>
                        <span class="icon">
                            <span class="icon ico-user integration-icon"
                                title="Manually Updated"
                                data-role="tooltip"
                                data-auto-hide="true"
                                data-position="top">
                            </span>
                        </span>
                    # } #                 
                </span>
                <span class="card-value float-right">
                    <span class="card-value font-small text-align-left">
                        # let formattedValue = Align.Util.KPIHelper.formatValue(Value, false); #
                        # let formattedShorthandValue = Align.Util.KPIHelper.formatValue(Value, true); #
                        <span class="ellipsis-text display-block" title="#=formattedValue#" data-position="top">#=formattedShorthandValue#</span>
                    </span>
                </span>
            </div>
        </div>
    </script>

    <!--#include file="/Templates/templateCompanyPersonsDropdown.template.html"-->
    <!--#include file="/Templates/kpiTemplates.template.html"-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/exceljs/4.4.0/exceljs.min.js"></script>
    <script src="/js/Generated/EditCompanyMetricDialog.js" type="text/javascript"></script>

  <style>
/* Modal Styling */
.modal {
    display: none;
    position: fixed;
    z-index: 1050;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);
    overflow: auto;
    padding-top: 50px;
    animation: fadeIn 0.3s ease-in-out;
    backdrop-filter: blur(5px);

    display: flex;
    justify-content: center;
    align-items: center;
}
 .list-container {
            border: 1px solid #e0e0e0;
            border-radius: 4px;
            max-width: 600px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        
        .list-header {
            background-color: #f9f9f9;
            padding: 15px;
            border-bottom: 1px solid #e0e0e0;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        
        .header-title {
            display: flex;
            align-items: center;
        }
        
        .header-title h2 {
            margin: 0;
            font-size: 18px;
        }
        
        .help-icon {
            margin-left: 10px;
            font-size: 18px;
            text-decoration: none;
            color: #007bff;
        }
        
        .list-items {
            padding: 0;
        }
        
        .list-item {
            padding: 12px 15px;
            border-bottom: 1px solid #e0e0e0;
            display: flex;
            align-items: center;
            cursor: pointer;
        }
        
        .list-item:last-child {
            border-bottom: none;
        }
        
        .list-item:hover {
            background-color: #f5f5f5;
        }
        
        .list-footer {
            background-color: #f9f9f9;
            padding: 15px;
            border-top: 1px solid #e0e0e0;
            text-align: right;
        }
/* Modal Content */
.modal-content {
    background: #fff;
    margin: auto;
    padding: 20px;
    width: 95%;
    max-width: 900px;
    border-radius: 12px;
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
    animation: slideDown 0.3s ease;
}

/* Modal Header */
.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 10px;
    border-bottom: 2px solid #f2f2f2;
}

/* Title */
h2 {
    margin: 0;
    font-size: 22px;
    color: #333;
}

/* Close Button */
.close-btn {
    font-size: 26px;
    font-weight: bold;
    color: #999;
    cursor: pointer;
    transition: color 0.3s ease;
}

.close-btn:hover {
    color: #ff4d4d;
}

/* Shared Targets List */
#sharedTargetsList {
    max-height: 400px;
    overflow-y: auto;
    padding: 10px;
    font-size:small;
    /*border-radius: 8px;*/
    /*background-color: #f9f9f9;*/
    /*box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.1);*/
}

/* List Items */
.dropdown-item {
    padding: 12px 15px;
    border-bottom: 1px solid #ddd;
    display: flex;
    align-items: center;
    transition: background-color 0.2s, transform 0.2s;
    border-radius: 6px;
}

.dropdown-item:last-child {
    border-bottom: none;
}

.dropdown-item:hover {
    background-color: #e6f7ff;
    transform: scale(1.02);
}

/* Profile Picture */
.profile-pic {
    /*width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 15px;
    object-fit: cover;
    border: 2px solid #007bff;*/

    width: 34px;
    height: auto;
    display: block;
    overflow: hidden;
    border-radius: 50% !important;
}

/* Icons */
.icon {
    font-size: 18px;
    cursor: pointer;
    transition: transform 0.3s, color 0.3s;
    margin-left: 10px;
}

.icon:hover {
    color: #007bff;
    transform: scale(1.2);
}

/* Modal Footer */
.modal-footer {
    display: flex;
    justify-content: flex-end;
    padding-top: 10px;
    border-top: 2px solid #f2f2f2;
    margin-top: 15px;
}

/* Buttons */
.btn {
    padding: 8px 16px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    transition: background 0.3s ease;
}

.btn-close {
    background-color: #ff4d4d;
    color: white;
}

.btn-close:hover {
    background-color: #cc0000;
}

/* Animations */
@keyframes fadeIn {
    from { opacity: 0; }
    to { opacity: 1; }
}

@keyframes slideDown {
    from { transform: translateY(-20px); opacity: 0; }
    to { transform: translateY(0); opacity: 1; }
}

/* Responsive Design */
@media screen and (max-width: 768px) {
    .modal-content {
        width: 90%;
    }

    h2 {
        font-size: 20px;
    }

    .dropdown-item {
        padding: 10px;
    }
}

/* Hide default checkbox */
input[type="checkbox"] {
    appearance: none;
    width: 5px;
    height: 5px;
    border: 2px solid #f7941d;
    border-radius: 2px;
    cursor: pointer;
    position: relative;

}

/* Checkbox checked state */
input[type="checkbox"]:checked {
    background-color: #F7941D; /* Box color when checked */
    /*border: 2px solid #f7941d;*/
}

/* Add a white checkmark when checked */
input[type="checkbox"]:checked::after {
    content: '✔';
    color: white;
    font-size: 16px;
    position: absolute;
    left: 2px;
    top: -7px;
    font-weight: bold;
}
/* Content visibility classes */
.show-metric .metric-section {
    display: block !important;
}

.show-metric .visual-section {
    display: none !important;
}

.show-visual .visual-section {
    display: block !important;
}

.show-visual .metric-section {
    display: none !important;
}

/* Tab button style */
.tab-button.active {
    font-weight: bold !important;
    color: #23a99a !important;
    border-bottom: 3px solid #23a99a !important;
}

.tab-button:not(.active) {
    color: #333 !important;
    border-bottom: 3px solid transparent !important;
}

/* Simple tabs container */
.simple-tabs {
    border-bottom: 1px solid #e0e0e0;
}


.dropdown-container {
    position: relative;
    width: 100%;
    /*max-width: 800px;*/
    margin: 0 auto;
}

.dropdown-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    border-bottom: 1px solid #ddd;
}

/* Clean, simple styling for the shared targets component */
.shared-targets-container {
    /*width: 100%;*/
    /*max-width: 800px;*/
    margin: 0 auto;
    font-family: Arial, sans-serif;
    /*margin:10px;*/
}

.shared-targets-label {
    display: block;
    font-size: 12px;
    font-weight: bold;
    color: #333;
}

.search-input {
    width: 100%;
    padding: 8px 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    font-size: 14px;
    margin-bottom: 2px;
}

.targets-list-container {
    border: 1px solid #ddd;
    border-radius: 4px;
    max-height: 300px;
    overflow-y: auto;
}

.targets-list .dropdown-item {
    padding: 10px;
    border-bottom: 1px solid #eee;
    cursor: pointer;
}

.targets-list .dropdown-item:hover {
    background-color: #f9f9f9;
}

.targets-list .dropdown-item:last-child {
    border-bottom: none;
}

.no-targets, .no-results {
    padding: 15px;
    text-align: center;
    color: #666;
    font-style: italic;
}

/* Custom scrollbar for WebKit browsers */
.targets-list-container::-webkit-scrollbar {
    width: 6px;
}

.targets-list-container::-webkit-scrollbar-track {
    background: #f1f1f1;
}

.targets-list-container::-webkit-scrollbar-thumb {
    background: #ccc;
    border-radius: 4px;
}

.targets-list-container::-webkit-scrollbar-thumb:hover {
    background: #aaa;
}

.no-targets, .no-results {
    padding: 15px;
    text-align: center;
    color: #6c757d;
    font-style: italic;
}

.dropdown-item:hover {
    background-color: #f8f9fa;
}
header {
    background-color: #343a40;
    color: white;
    padding: 16px;
    text-align: center;
    font-size: 24px;
    font-weight: bold;
    letter-spacing: 1px;
  }
  
  .table-responsive {
  overflow-y: auto;
  max-height: 500px;
  width: 100%;
  display: block;
}

table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
  table-layout: auto; /* Allows natural column sizing */
}

thead tr th {
  position: sticky;
  top: 0;
  background-color: #f1f1f1;
  z-index: 10;
  box-shadow: 0 1px 0 rgba(0, 0, 0, 0.1);
  white-space: nowrap;
}

th, td {
  border: 1px solid #dee2e6;
  padding: 10px;
  text-align: left;
  vertical-align: top;
  white-space: normal;             /* Allow text to wrap */
  word-wrap: break-word;           /* Ensure long words break */
  overflow-wrap: break-word;
}

th {
  background-color: #f1f1f1;
  color: #333;
  font-size: 13px;
  font-weight: 600;
}

.change-details-col {
  width: 50%;
  word-break: break-word;
}

tr:hover {
  background-color: #f9f9f9;
}

/* Optional: Add basic button styling for toggle */
.log-toggle-header {
  display: flex;
  align-items: center;
  font-weight: bold;
  font-size: 12px;
  cursor: pointer;
  margin-bottom: 5px;
  margin-top: 15px;
}

.label-text {
  white-space: nowrap;
  margin-right: 6px;
}

.k-icon {
  font-size: 12px;
  margin-right: 10px;
  color: black;
}

.line {
  flex: 1;
  height: 1px;
  background-color: black;
}
.radio input[disabled] {
    cursor: not-allowed;
}
/* Adds border to all dropdown items except the last one */
#historicalValuesDropdown .dropdown-item:not(:last-child) {
    border-bottom: 1px solid #eee !important;
}

</style>

</asp:Content>
