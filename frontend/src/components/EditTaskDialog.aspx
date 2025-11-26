<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPopup.Master" %>
<%@ OutputCache CacheProfile="Page"%>


<asp:Content ID="Content1" ContentPlaceHolderID="MasterContent" runat="server">
    <div class="pop-wrapper" id="editTaskDialogContent" data-bind="visibility: loaded, showProgress: showProgress">
        <div class="pop-content">
            <h2>Add/Edit Task
            </h2>
            <ul class="form no-style">
                <li>
                    <div class="label">
                        Short Task Name
                    </div>
                    <div class="check-textbox middle">
                        <div class="check-column star">
                            <input title="Mark this task as a top task" type='checkbox' data-role="uniform" data-bind="value: task.IsTopTask" />
                        </div>
                        <input class="autocomplete-off" type="text" placeholder="Short Task Name" autofocus data-bind="value: task.ShortDescription" maxlength="1000"  />
                    </div>
                </li>
                <li>
                    <div class="label">
                        Due Date
                    </div>
                    <input id = "inputdatepicker" data-role="datepicker"  data-bind="value: task.EndDate" />
                </li>
                <li class="drawer-element-fit-content">
                    <label class="check-textbox">
                        <input type='checkbox' data-role="uniform" data-bind='value: task.Recurring, events: { click: clickMakeTaskRecurring }' />
                        <span class="check-label">Make this a recurring task
                        </span>
                    </label>
                </li>
                <li data-bind="visible: task.Recurring" style="clear: both">
                    <p>
                        You are creating a recurring task! Recurring tasks will only display one at a time, but the new tasks will be shown as soon as the prior task is completed. Please do not use a recurring task as part of a task driven priority.
                    </p>
                    <ul class="form no-style full">
                        <li>
                            <select class="dropdown"
                                data-value-primitive="true"
                                data-option-label="Select Frequency"
                                data-bind='value: task.Frequency'
                                data-role='dropdownlist'>
                                <option value="daily">Daily</option>
                                <option value="weekly">Weekly</option>
                                <option value="everyotherweek">Every Other Week</option>
                                <option value="monthly">Monthly</option>
                                <option value="quarterly">Quarterly</option>
                                <option value="yearly">Yearly</option>
                            </select>
                        </li>
                        <li>
                            <input class="input"
                                type="text"
                                data-role="numerictextbox"
                                data-bind="value: task.Recurrences"
                                min="0"
                                placeholder="0"
                                data-format="###" />
                        </li>
                    </ul>
                </li>
                <li data-bind="visible: task.Recurring">
                    <p data-bind="text: getRecurrenceInfo">
                    </p>
                </li>
                <li>
                    <div class="label">
                        Assigned To
                    </div>
                    <input id="taskOwner"
                        class="input autocomplete-off inputFieldClass"
                        spellcheck="false"
                        data-role="autocomplete"
                        data-template="personTemplate"
                        data-placeholder="Search or Invite Team Members"
                        data-header-template="InviteHeaderTemplate"
                        data-text-field="Name"
                        data-filter="contains"
                        data-bind="value: owner, source: dsSearchPersons, events: { filtering: toggleInviteInput, select: onSelectPerson, change: onChangeOwner }"
                        style="width: 100%;" />
                    <ul class="tags" data-bind="source: dsTaskAssignees" data-template='personListTemplate'></ul>
                </li>
                <li class="drawer-element-fit-content" data-bind="visible: IsNewTask">
                    <label class="check-textbox">
                        <input type='checkbox' data-role="uniform" data-bind='value: assignToAll, events: {change: clickAssignMultipleUsers}' />
                        <span class="check-label">Assign to all Users
                        </span>
                    </label>
                </li>
                <li class="drawer-element-fit-content" data-bind="visible: IsNewTask">
                    <label class="check-textbox">
                        <input type='checkbox' data-role="uniform" data-bind='value: assignToAllAdmins, events: {change: clickAssignMultipleUsers}' />
                        <span class="check-label">Assign to all Admins
                        </span>
                    </label>
                </li>
                <li>
                </li>
                <li data-bind="visible: bIsOKRFeatureEnabled">
                    <div class="label">
                        Align to an Objective or Key Result
                    </div>
                    <span data-bind="visible: isUnsavedPriority">This task will be attached to the new OKR.</span>
                    <input id="okr"
                        class="input autocomplete-off"
                        data-template="priorityAutoCompleteTemplate"
                        placeholder="Start typing to search OKRs"
                        data-role="autocomplete"
                        data-text-field="Name"
                        data-bind="value: rock, invisible: isUnsavedPriority, source: dsPriorities, events: { change: onChangeRock, select: onSelectOKR }" />

                </li>
                <li data-bind="visible: isPreconBrandWithoutOKR">
                    <div class="label">
                        Align to Project or Phase
                    </div>
                    <span data-bind="visible: isUnsavedPriority">This task will be attached to the new Project or Phase.</span>
                    <input id="project-phase"
                        class="input autocomplete-off"
                        data-template="priorityAutoCompleteTemplate"
                        placeholder="Start typing to search Projects or Phases"
                        data-role="autocomplete"
                        data-text-field="Name"
                        data-bind="value: rock, invisible: isUnsavedPriority, source: dsPriorities, events: { change: onChangeRock, select: onSelectProjectPhase }" />
                </li>
                <li data-bind="visible: isDefaultBrand">
                    <div class="label">
                        Align to a Priority
                    </div>
                    <span data-bind="visible: isUnsavedPriority">This task will be attached to the new priority.</span>
                    <input id="priority"
                        class="input autocomplete-off"
                        data-template="priorityAutoCompleteTemplate"
                        placeholder="Start typing to search Priorities"
                        data-role="autocomplete"
                        data-text-field="Name"
                        data-bind="value: rock, invisible: isUnsavedPriority, source: dsPriorities, events: { change: onChangeRock, select: onSelectPriority }" />
                </li>
                <li data-bind="visible: bIsHuddlesAssociatedTasksEnabled">
                    <div class="label">
                        Align to a Huddle
                    </div>
                    <input id="huddle-related"
                        class="input autocomplete-off" 
                        data-template="huddleListTemplate" 
                        placeholder="Start typing to search Huddles" 
                        data-role="autocomplete" 
                        data-text-field="Name" 
                        data-bind="visible: isNotAMemberOfHuddle, value: selectedHuddle, source: dsHuddles, events: { change: onChangeHuddle }" />                  
                    <input class="input" id="huddle-related-readonly" data-bind="invisible: isNotAMemberOfHuddle, enabled:isNotAMemberOfHuddle "/> 
                </li>
                <li data-bind="display:hasTaskProvider" style="display:none;">
                    <div class="label">
                        Sync Category
                    </div>
                    <input class="input" data-role="dropdownlist" data-option-label="{ID: -1, Name: 'Select Sync Category...'}" data-auto-bind="false" data-value-field="ID" data-text-field="Name" data-bind="value: SyncCategory, source: dsSyncCategories" />

                </li>
                <li>
                    <ul class="form no-style">
                        <li data-bind="invisible: IsNewTask">
                            <label class="check-textbox">
                                <input type='checkbox' data-role="uniform" data-bind='value: task.IsComplete' />
                                <span class="check-label">Mark Task as Complete
                                </span>
                            </label>
                        </li>
                        
                    </ul>
                </li>
                
                <!--VISIBILITY / TEAMS-->
                <li data-bind="visible: bIsTeamsFeatureEnabled">
                    <div class="module padded-wrapper">
                        <ul class="module-inner-less-padding no-style">
                            <li>
                                <label>
                                    <div class="label">
                                        Visibility
                                    </div>
                                    <input class="dropdown"
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
                            <li>
                                <label data-bind="visible: taskHasPriorityLink">
                                    <div class="label" data-bind="visible: taskHasPriorityLink">
                                        Visibility settings are based on the linked Priority and can not be changed here. 
                                    </div>
                                </label>
                            </li>
                            <li data-bind="visible: isTeamsVisible">
                                <p>
                                    <em>Select one or more teams that will have access to this Task.
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
                                    <em>Select the users who will have access to this Task. (The Task owner will always have access.)
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
                                                <span data-bind="text: task.ApprovedUsers.length"></span> users selected
                                            </h5>
                                            <ul class="card-list-wrapper user-list scroll" data-role="listview" data-bind="source: task.ApprovedUsers" data-template="templateApprovedUsersSelected">
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </li>
                <li>
                    <label>
                        <div class="label">
                            Notes
                        </div>
                        <div class="textarea"
                            data-role="aligneditor"
                            data-bind="value: task.Notes">
                        </div>
                    </label>
                </li>

                <%--Documents Section--%>
                <%-- VSS 07th Nov 2025 - Documents section controlled by feature flag --%>
                <li data-bind="visible: isDocumentManagementFeatureEnabled">
                    <div class="margin-top">
                        <div style="margin-bottom: 8px;">
                            <span class="label">Documents</span>
                            <span class="icon ico-plus-circle" data-bind="click: clickAddDocument" style="display: none; cursor: pointer; color: #23A99A; margin-left: 8px; vertical-align: middle;" id="addTaskDocumentBtn" title="Add Document"></span>
                        </div>

                        <%-- Warning for staged documents --%>
                        <div data-bind="visible: stagedDocuments.length" style="background-color: #fff3cd; border-left: 3px solid #ffc107; padding: 6px 10px; margin-bottom: 8px; border-radius: 3px;">
                            <span style="color: #856404; font-size: 14px;">
                                <strong>Note:</strong> If you cancel without saving, you will lose the <span data-bind="text: stagedDocuments.length"></span> document(s) you have uploaded.
                            </span>
                        </div>

                        <%-- Empty State --%>
                        <div data-bind="invisible: hasTaskDocuments">
                            <p class="gray italic">No documents to show. Click on <span class="icon ico-plus-circle" style="color: #23A99A;"></span> icon to add document.</p>
                        </div>

                        <%-- Documents Grid (Two Columns) --%>
                        <div class="task-documents-grid" data-bind="visible: hasTaskDocuments">
                            <ul class="list-view no-style" data-bind="source: dsTaskDocuments" data-template="templateTaskDocument">
                            </ul>
                        </div>
                    </div>
                </li>

                <!--updated by vivek - start - Task Change Logs section (following EditCompanyMetricDialog pattern) -->
                <li data-bind="visible: IsTaskLogsVisible">
                    <div id="toggleLogBtn" class="log-toggle-header">
                        <span class="label">Change Logs</span>
                        <span id="logCountBadge"
                              title="Log Count"
                              data-bind="text:logCount"
                              style="background-color: #d4edda; 
                                     color: #000000;
                                     padding: 2px 6px;
                                     border-radius: 5px;
                                     font-size: 12px;">
                            <strong></strong>
                        </span>
                        <span id="toggleArrow" title="Show Logs" class="k-icon k-i-arrow-60-down"></span>
                        <div class="log-line"></div>
                    </div>

                    <div class="log-container" id="logContainer" style="display: none;">
    <div class="table-responsive">
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Modified By</th>
                    <th class="change-details-col">Change Details</th>
                </tr>
            </thead>
            <tbody id="logListContainer">
                <!-- Injected rows -->
            </tbody>
        </table>
    </div>
</div>
                </li>
                <!--updated by vivek - end - Task Change Logs section -->
            </ul>
        </div>

        <%-- Task Document Template (Two Column Layout) --%>
        <script id="templateTaskDocument" type="text/x-kendo-template">
            # if (typeof data !== 'undefined' && data !== null && data.ID) { #
            <li class="task-document-item" data-document-id="#= ID #">
                <div class="task-document-content">
                    <span class="task-document-name"
                          data-tooltip-description="#: Description || '' #"
                          data-tooltip-date="#= kendo.toString(new Date(CreatedDate), 'MM/dd/yyyy') #"
                          data-tooltip-owner="#: DocumentOwnerName || '' #">
                        #= DocumentName #
                    </span>
                    <div class="task-document-actions">
                        # if (DocumentType === 'File') { #
                            <span class="icon ico-download" title="Download" data-bind="click: clickViewTaskDocument" style="cursor: pointer;"></span>
                        # } else { #
                            <span class="icon ico-link" title="Open Link" data-bind="click: clickViewTaskDocument" style="cursor: pointer;"></span>
                        # } #
                        # if (CanEdit) { #
                            <span class="icon ico-edit-" title="Edit" data-bind="click: clickEditTaskDocument" style="cursor: pointer;"></span>
                            <span class="icon ico-trash-" title="Delete" data-bind="click: clickDeleteTaskDocument" style="cursor: pointer;"></span>
                        # } #
                    </div>
                </div>
            </li>
            # } #
        </script>

        <style>
            /* Task Documents Two-Column Grid */
            .task-documents-grid {
                margin-top: 5px;
            }

            .task-documents-grid .list-view {
                display: grid;
                grid-template-columns: repeat(2, 1fr);
                column-gap: 6px;
                row-gap: 4px;
                padding: 0;
                margin: 0;
            }

            .task-document-item {
                border: 1px solid #e0e0e0;
                border-radius: 5px;
                padding: 10px 10px;
                background: #f9f9f9;
                list-style: none;
                overflow: hidden; /* Prevent content from breaking layout */
                min-height: 40px;
                transition: background-color 0.2s ease;
            }

            .task-document-item:hover {
                background: #ffffff;
            }

            .task-document-content {
                display: flex;
                justify-content: space-between;
                align-items: center;
                gap: 8px;
                max-width: 100%; /* Ensure content stays within bounds */
            }

            .task-document-name {
                display: block;
                flex: 1;
                min-width: 0;
                color: #333;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                word-wrap: break-word; /* Break long words if needed */
                max-width: 100%; /* Prevent overflow */
                font-size: 14px;
                line-height: 1.25;
            }

            .task-document-actions {
                display: flex;
                gap: 8px;
                flex-shrink: 0;
                align-items: center;
            }

            .task-document-actions .icon {
                color: #666;
                font-size: 16px;
            }

            .task-document-actions .icon:hover {
                color: #23A99A;
            }
        </style>

        <div class="button-footer">
            <div class="button-tray">
                <button class="button btn-filled--m" data-bind="click: clickSaveTask">
                    <span class="icon ion-checkmark-circled"></span>
                    <span>Save
                    </span>
                </button>
                <button class="button btn-filled--m" data-bind="click: clickSaveAndNew">
                    <span class="icon ico-plus-circle"></span>
                    <span>Save and Add Another
                    </span>
                </button>
                <button class="button btn-ghost--m" data-bind="invisible: IsNewTask, events: { click: clickPrintTask }">
                    <span class="icon ico-printer"></span>
                    <span>Print Task
                    </span>
                </button>
            </div>
            <div class="cancel-actions">
                <button class="cancel" data-bind="click: clickCloseDialog">
                    Cancel
                </button>
            </div>
        </div>
    </div>
    <script id="personListTemplate" type="text/x-kendo-template">
        <li>
            <button data-bind="click:clickRemovePerson">
                <span class="icon ion-close-circled"></span>
                <span data-bind='html:Name'></span>
            </button>
        </li>
    </script>
    <script id="huddleListTemplate" type="text/x-kendo-template">
        <div>
            <span data-bind='html:Name'></span>
        </div>
    </script>
    
    <!--#include file="/Templates/templateApprovedUsers.template.html"-->
    <!--#include file="/Templates/priorityAutoComplete.template.html"-->
    <!--#include file="/Templates/templateCompanyPersonsDropdown.template.html"-->

    <script type="text/javascript">
        $(function () {

            var $content = $('#editTaskDialogContent');
            var secureAPI = Align.Web.Services.SecureAPI;
            var api = Align.Web.API;
            var featureFlagEvaluator = Align.FeatureFlags.Evaluator;
            var taskBeforeEdit = null;
            
            var vm = kendo.observable({
                loaded: true,
                currentPerson: masterPerson,
                currentCompany: masterCompany,
                bIsTeamsFeatureEnabled: true,
                bIsOKRFeatureEnabled: false,
                bIsHuddlesFeatureEnabled: false,
                isDocumentManagementFeatureEnabled: false, // VSS 07th Nov 2025 - Document Management feature flag
                isPreconBrandWithoutOKR: function () {
                    return masterCompany.AlignBrandID === 6 && !vm.get('bIsOKRFeatureEnabled');
                },
                isDefaultBrand: function () {
                    return masterCompany.AlignBrandID !== 6 && !vm.get('bIsOKRFeatureEnabled');
                },
                //updated by vivek - start - Task Change Logs properties (following EditCompanyMetricDialog pattern)
                IsTaskLogsVisible: false,
                logCount: 0,
                changeLogs: [],
                //updated by vivek - end - Task Change Logs properties
                //VSS 29th Oct 2025 - start - Task Documents properties
                stagedDocuments: [],
                hasTaskDocuments: false,
                dsTaskDocuments: new kendo.data.DataSource({
                    transport: {
                        read: function (options) {
                            var task = vm.get('task');
                            var isNewTask = !task || !task.ID || task.ID <= 0;

                            if (isNewTask) {
                                // For new tasks, fetch staged documents by IDs
                                var stagedDocIDs = vm.get('stagedDocuments') || [];

                                if (stagedDocIDs.length === 0) {
                                    vm.set('hasTaskDocuments', false);
                                    options.success([]);
                                    return;
                                }

                                // Call API to get documents by IDs
                                secureAPI.GetDocumentsByIDs(stagedDocIDs)
                                    .done(function (results) {
                                        vm.set('hasTaskDocuments', results && results.length > 0);
                                        options.success(results || []);
                                    })
                                    .fail(function (error) {
                                        console.error("Failed to load staged documents:", error);
                                        vm.set('hasTaskDocuments', false);
                                        options.success([]);
                                    });
                                return;
                            }

                            // VSS 07th Nov 2025: Commented out tag filtering as per requirement change
                            // VSS 2025-11-05: Collect unique tags from Priority (Rock) and Huddle
                            // Tags are populated in task.RockTags and task.HuddleTags from backend (TaskService)
                            /*
                            var tagIDs = [];
                            var tagsMap = {}; // To avoid duplicates

                            // Get tags from Priority (Rock) if assigned
                            var rock = vm.get('rock');
                            if (rock && rock.Tags && rock.Tags.length > 0) {
                                rock.Tags.forEach(function(tag) {
                                    if (!tagsMap[tag.ID]) {
                                        tagsMap[tag.ID] = true;
                                        tagIDs.push(tag.ID);
                                    }
                                });
                            }

                            // Get tags from Huddle if assigned
                            var selectedHuddle = vm.get('selectedHuddle');
                            if (selectedHuddle && selectedHuddle.Tags && selectedHuddle.Tags.length > 0) {
                                selectedHuddle.Tags.forEach(function(tag) {
                                    if (!tagsMap[tag.ID]) {
                                        tagsMap[tag.ID] = true;
                                        tagIDs.push(tag.ID);
                                    }
                                });
                            }

                            // Convert tag IDs array to comma-separated string
                            var tagIDsString = tagIDs.join(',');
                            */

                            // Call API to get documents for this Task (no tag filtering)
                            secureAPI.GetDocumentsBySourceEntity(task.ID, "AlignTasks", '')
                                .done(function (results) {
                                    vm.set('hasTaskDocuments', results && results.length > 0);
                                    options.success(results || []);
                                })
                                .fail(function (error) {
                                    console.error("Failed to load task documents:", error);
                                    vm.set('hasTaskDocuments', false);
                                    options.error(error);
                                });
                        }
                    },
                    schema: {
                        model: {
                            id: "ID"
                        }
                    }
                }),
                //VSS 29th Oct 2025 - end - Task Documents properties
                visibilityOptions: [
                    { id: 1, text: "Everyone", value: "1", isDisabled: false },
                    { id: 2, text: "Selected Users", value: "2", isDisabled: false },
                    { id: 3, text: "Selected Teams", value: "3", isDisabled: false }
                ],
                isPrioritySecurityDisabled: false,
                disableOptions: function (e) {
                    if (e.dataItem.isDisabled) {
                        e.preventDefault();
                    }
                },
                taskProviderID: -1,
                hasTaskProvider: function () {
                    var hasProvider = false;
                    //2020-01-16 RJK:  For new Task, if there is a Task Provider, return true because the current User is assigned by default.
                    var currentPersonObject = vm.get("currentPerson");

                    if (currentPersonObject && currentPersonObject.TaskProviderID > 0) {
                        //We have a Task Provider.  Is it a new Task or is the current Person the Owner of an existing Task?  If so, return true.
                        if (vm.get("IsNewTask") || vm.get("owner.ID") === currentPersonObject.ID) {
                            hasProvider = true;
                        }
                    }

                    return hasProvider;
                },
                //updated by vivek - start - Task Change Logs method
                loadChangeLogs: function () {
                    console.log("[TaskChangeLogs] Loading change logs for task ID:", vm.get("task.ID"));

                    // Reset the logs section visibility and state first
                    vm.set("IsTaskLogsVisible", false);
                    vm.set("logCount", 0);
                    vm.set("changeLogs", []);

                    // Reset the UI elements
                    const logContainer = document.getElementById("logContainer");
                    const toggleArrow = document.getElementById("toggleArrow");

                    if (logContainer) {
                        logContainer.style.display = "none";
                    }
                    if (toggleArrow) {
                        toggleArrow.className = "k-icon k-i-arrow-60-down";
                        toggleArrow.title = "Show Logs";
                    }

                    if (!vm.get("IsNewTask") && vm.get("task.ID") > 0) {
                        var taskId = vm.get("task.ID");

                        secureAPI.GetTaskChangeLogs(taskId)
                            .done(function (logs) {
                                console.log("[TaskChangeLogs] Successfully retrieved", logs ? logs.length : 0, "log entries from backend");

                                var logCount = logs ? logs.length : 0;
                                vm.set("logCount", logCount);
                                vm.set("changeLogs", logs || []);

                                if (!logCount || logCount <= 0) {
                                    vm.set("IsTaskLogsVisible", false);
                                } else {
                                    vm.set("IsTaskLogsVisible", true);
                                    renderTaskLogs(logs);

                                    // Ensure tooltip is set after logs are loaded
                                    setTimeout(function () {
                                        const toggleArrow = document.getElementById("toggleArrow");
                                        if (toggleArrow && !toggleArrow.title) {
                                            toggleArrow.title = "Show Logs";
                                        }
                                    }, 50);
                                }
                            })
                            .fail(function (error) {
                                console.error("[TaskChangeLogs] API call failed:", error);
                                vm.set("IsTaskLogsVisible", false);
                                vm.set("logCount", 0);
                                vm.set("changeLogs", []);
                            });
                    }
                },
                //updated by vivek - end - Task Change Logs method
                clickPrintTask: function (e) {
                    var task = vm.get("task");
                    var drawer = new radolo.drawer(Align.Common.DrawerOptions.printDrawer);
                    
                    var printPageURL = "/Application/TaskPrintDialog.aspx?taskID=" + task.ID;
                                       
                    $.pubsub.subscribeSingle('NeedPrintPageURL', function () {
                        $.pubsub.publish("PrintPageURLReady", printPageURL);
                    });

                    $.pubsub.subscribeSingle('PrintDialogComplete', function () {
                        drawer.close();
                    });

                    drawer.open();
                },
                clickMakeTaskRecurring: function () {
                    vm.set("task.Recurrences", 0);
                },
                onEditTaskEntry: function () {
                    var person = vm.get("currentPerson");
                    var task = vm.get('task');
                    
                    if (task != null && task.OwnerID != person.ID) {
                         secureAPI.GetPerson(task.OwnerID).done(function (assigned) {
                             vm.set("assignedPerson", assigned);
                             vm.dsTaskAssignees.add(vm.get("assignedPerson"));
                            
                         }).fail(function (e) {
                             vm.set("showProgress", false);
                             $.radoloCommon.showError(e);
                         });
                    }
                    else {
                        vm.dsTaskAssignees.add(person);
                    }
                 
                    //we do not set the owner becasue it will populate the "assign to" field 
                },
                onSelectPerson: function (e) {
                    if (e.person) {
                        var person = e.person;
                    }
                    else {
                        var person = e.sender.dataItem(e.item.index());
                    }
                    //if a tasks already exists, you cannot add multiple people to it- you can only add assignees if the task is new
                    if (vm.get("IsNewTask")) {
                        var dataItem = vm.get('dsTaskAssignees').get(person.ID);
                        if (!dataItem) {
                            vm.dsTaskAssignees.add(person);
                        }
                        vm.set('owner', "");
                        vm.set('isTaskSecurityDisabled', false);
                        if (vm.get('dsTaskAssignees').data().length > 1) {
                            vm.set('selectedVisibility', vm.get('visibilityOptions')[0]);
                            vm.set("task.Visibility", "Public");
                            vm.set('isTaskSecurityDisabled', true);
                        }
                    }
                    else {
                        //An admin can still be able to edit the existing task. 
                        //Whenever the count of dsTaskAssignees is 0 it means all the assignees are removed and so 
                        //selected person should be added to the assignees.
                        if (vm.get('dsTaskAssignees').data().length < 1) {
                            if (!dataItem) {
                                vm.dsTaskAssignees.add(person);
                            }
                        }
                        vm.set("owner", person);
                        vm.set("task.Owner", person.Name);
                        vm.set("task.OwnerID", person.ID);
                        vm.set('task.OwnerPicture', person.Picture + "&width=34&height=34");
                        vm.get("dsUserTeams").read();
                        if (!masterPerson.IsAdministrator && masterPerson.ID != person.ID) {
                            vm.set('isTaskSecurityDisabled', true);
                        }

                        // set dropdown to readonly
                        //if (!e) {
                        //    $("#taskOwner")[0].setAttribute('readonly', true);
                        //} else {
                        //    e.sender.element[0].setAttribute('readonly', true);
                        //}
                        // FIXED: set dropdown to readonly with proper null checking
                        try {
                            if (!e) {
                                // Direct element selection
                                var taskOwnerElement = $("#taskOwner")[0];
                                if (taskOwnerElement) {
                                    taskOwnerElement.setAttribute('readonly', true);
                                }
                            } else {
                                // Check if sender and element exist before accessing
                                if (e.sender && e.sender.element && e.sender.element[0]) {
                                    e.sender.element[0].setAttribute('readonly', true);
                                } else if (e.sender && e.sender.wrapper && e.sender.wrapper[0]) {
                                    // Alternative: try wrapper if element is not available
                                    e.sender.wrapper[0].setAttribute('readonly', true);
                                } else {
                                    // Fallback: try direct element selection
                                    var taskOwnerElement = $("#taskOwner")[0];
                                    if (taskOwnerElement) {
                                        taskOwnerElement.setAttribute('readonly', true);
                                    }
                                }
                            }
                        } catch (error) {
                            console.warn('Could not set taskOwner to readonly:', error);
                            // Don't throw - this is a UI enhancement, not critical functionality
                        }

                        if (vm.isUsersVisible) {
                            //Make sure to read the available users each time we change a owner
                            //because with the same source we might not be able to access the prvious owner
                            //as we would have removed it from the source when added as a owner
                            //vm.dsCompanyPersonsAvailableForSecureTasks.read(); 
                            var isCurrentUserInApprovedUsers = false;
                            var isAssigneeInApprovedUser = false;
                            var availableAssignees = vm.get('dsTaskAssignees.data()');
                            var currentApprovedUsers = vm.get('task.ApprovedUsers');
                            var assigneesNotInApprovedUsers = [];
                            //Make sure to add all the assignees to Users with Access
                            $.each(availableAssignees, function (index, item) {

                                currentApprovedUsers.forEach(function (person) {
                                    if (person.ID === item.ID) {
                                        isAssigneeInApprovedUser = true;
                                    }

                                });
                                if (!isAssigneeInApprovedUser) {
                                    assigneesNotInApprovedUsers.push(item);
                                }
                            });
                            assigneesNotInApprovedUsers.forEach(function (person) {

                                vm.get("task.ApprovedUsers").push(person);
                                //currentApprovedUsers.push(person);
                                var availableUsers = vm.get('dsCompanyPersonsAvailableForSecureTasks.data()');
                                //once added the current user remove from the source
                                availableUsers.forEach(function (item) {
                                    if (person.ID === item.ID) {
                                        vm.get("dsCompanyPersonsAvailableForSecureTasks").remove(item);

                                    }

                                });


                            });


                            //Make sure to add the current user to Approved Users if it doesnot exist
                            currentApprovedUsers.forEach(function (person) {
                                if (person.ID === masterPerson.ID) {
                                    isCurrentUserInApprovedUsers = true;
                                }
                            });

                            if (!isCurrentUserInApprovedUsers) {
                                vm.get("task.ApprovedUsers").push(vm.get("currentPerson"));
                                var availableUsers = vm.get('dsCompanyPersonsAvailableForSecureTasks.data()');
                                //once added the current user remove from the source
                                availableUsers.forEach(function (person) {
                                    if (person.ID === masterPerson.ID) {
                                        vm.get("dsCompanyPersonsAvailableForSecureTasks").remove(person);

                                    }

                                });
                            }

                        }

                    }
                },
                clickRemovePerson: function (e) {
                    var user = e.data;
                    vm.dsTaskAssignees.remove(user);
                    if (vm.dsTaskAssignees.data().length === 1) {
                        vm.set('isTaskSecurityDisabled', false);
                        vm.set('task.Owner', vm.dsTaskAssignees.data()[0].Name);
                        vm.set('task.OwnerID', vm.dsTaskAssignees.data()[0].ID);
                    }
                    else if (vm.dsTaskAssignees.data().length === 0) {
                        //No assignees.  Clear out task.Owner and task.OwnerID
                        vm.set('task.Owner', null);
                        vm.set('task.OwnerID', null);
                    }

                    vm.get("dsUserTeams").read();
                },
                onChangeOwner: function (e) {
                    //2020-01-28 RJK: Don't change the Owner if owner isn't an object, which means there's been no match. [APB-2899]  
                    if (vm.get('owner') && vm.get('owner').ID) {
                        vm.set('task.Owner', vm.get('owner.Name'));
                        vm.set('task.OwnerID', vm.get('owner.ID'));
                        // 2023-7-17 ND: Include OwnerPicture to display after add
                        vm.set('task.OwnerPicture', vm.get('owner.Picture') + '&width=34&height=34');
                    }
                    vm.set('owner', "");
                    vm.get("dsUserTeams").read();

                    if (!vm.get("owner")) {
                       e.sender.element[0].removeAttribute('readonly');
                    }
                    //if we are editing the current task's owner and the visibility is set to Users then 
                    //make sure to update Approved Users for that task
                    if (vm.isUsersVisible) {

                        //Make sure to read the available users each time we change a owner
                        //because with the same source we might not be able to access the prvious owner
                        //as we would have removed it from the source when added as a owner
                        //vm.dsCompanyPersonsAvailableForSecureTasks.read(); 
                        var isCurrentUserInApprovedUsers = false;
                        var isAssigneeInApprovedUser = false;
                        var availableAssignees = vm.get('dsTaskAssignees.data()');
                        var currentApprovedUsers = vm.get('task.ApprovedUsers');
                        var assigneesNotInApprovedUsers = [];
                        //Make sure to add all the assignees to Users with Access
                        $.each(availableAssignees, function (index, item) {
                            
                            currentApprovedUsers.forEach(function (person) {
                                if (person.ID === item.ID) {
                                    isAssigneeInApprovedUser = true;
                                }

                            });
                            if (!isAssigneeInApprovedUser) {
                                assigneesNotInApprovedUsers.push(item);
                            }
                        });
                        assigneesNotInApprovedUsers.forEach(function (person) {

                            vm.get("task.ApprovedUsers").push(person);
                            //currentApprovedUsers.push(person);
                            var availableUsers = vm.get('dsCompanyPersonsAvailableForSecureTasks.data()');
                            //once added the current user remove from the source
                            availableUsers.forEach(function (item) {
                                if (person.ID === item.ID) {
                                    vm.get("dsCompanyPersonsAvailableForSecureTasks").remove(item);

                                }

                            });


                        });



                        //Make sure to add the current user to Approved Users if it doesnot exist
                        currentApprovedUsers.forEach(function (person) {
                            if (person.ID === masterPerson.ID) {
                                isCurrentUserInApprovedUsers = true;
                            }
                        });

                        if (!isCurrentUserInApprovedUsers) {
                            vm.get("task.ApprovedUsers").push(vm.get("currentPerson"));
                            var availableUsers = vm.get('dsCompanyPersonsAvailableForSecureTasks.data()');
                            //once added the current user remove from the source
                            availableUsers.forEach(function (person) {
                                if (person.ID === masterPerson.ID) {
                                    vm.get("dsCompanyPersonsAvailableForSecureTasks").remove(person);

                                }

                            });
                        }

                    }

                },
                onChangeTeam: function (e) {
                    var task = vm.get(task);
                    var teams = vm.get('selectedTeams');
                    vm.set('task.Teams', teams);
                },
                onChangeRock: function (e) {
                    
                    //whenever a priority is added to Task we update task object with Rock's Visibility settings
                    if (vm.get('rock') != null) {
                        vm.set('task.RockName', vm.get('rock.Name'));
                        vm.set('task.RockID', vm.get('rock.ID'));
                        vm.set('task.RockVisibility', vm.get('rock.Visibility'));
                        vm.set('task.RockTeams', vm.get('rock.Teams'));
                        if (!vm.get("rock.ApprovedUsers")) {
                            vm.set("rock.ApprovedUsers", []);
                        }
                        vm.set('task.RockApprovedUsers', vm.get('rock.ApprovedUsers'));

                        if (vm.get('task.Visibility') == "Users") {
                            var rockApprovedUsers = [];

                            if (vm.get('rock.ApprovedUsers')) {
                                rockApprovedUsers = vm.get('rock.ApprovedUsers');
                            }
                            vm.set('task.ApprovedUsers', rockApprovedUsers);
                        }
                        vm.setVisibilityFromPriority(vm.get('task'));
                        vm.set('isTaskSecurityDisabled', true);
                        vm.set('taskHasPriorityLink', true);
                    }
                    else {
                        vm.set('isTaskSecurityDisabled', false); 
                        vm.set('taskHasPriorityLink', false);
                        //2020-10-25 RJK: Do this to ensure Approved Users UI is populated.
                        var approvedUsers = vm.get('task.ApprovedUsers');
                        vm.set('task.ApprovedUsers', []);
                        //J.S. error is thrown if approved users is null
                        if (approvedUsers) {
                            vm.set("task.ApprovedUsers", approvedUsers);
                        }
                        //vm.set('selectedVisibility', vm.get('visibilityOptions')[0]);
                         // remove readonly from dropdown
                        e.sender.element[0].removeAttribute('readonly');
                    }
                },
				//SY 2023-05-19 Add support for Associated Huddles in tasks
                onChangeHuddle: function (e) {
                    var huddle = vm.get('selectedHuddle');
                    if (huddle && huddle.ID > 0) {
                        vm.set("task.HuddleID", huddle.ID);
                        vm.set("task.HuddleType", huddle.HuddleType);
                        vm.set("task.Huddle", huddle.Name);
                    }
                    else {
                        //J.S. clear out the associated huddle related info if huddle ID isnt valid
                        vm.set("task.HuddleID", -1);
                        vm.set("task.HuddleType", null);
                        vm.set("task.Huddle", null);
                    }
                },
                onSelectPriority: function (e) {
                    // make priority dropdown readonly
                    if (!e) {
                        $("#priority")[0].setAttribute('readonly', true);
                    } else {
                        e.sender.element[0].setAttribute('readonly', true);
                    }
                },
                onSelectOKR: function (e) {
                    // make priority dropdown readonly
                    if (!e) {
                        $("#okr")[0].setAttribute('readonly', true);
                    } else {
                        e.sender.element[0].setAttribute('readonly', true);
                    }
                },
                onSelectProjectPhase: function (e) {
                    // make project-phase dropdown readonly for Precon brand
                    if (!e) {
                        var projectPhaseElement = document.getElementById("project-phase");
                        if (projectPhaseElement) {
                            projectPhaseElement.setAttribute('readonly', true);
                        }
                    } else {
                        e.sender.element[0].setAttribute('readonly', true);
                    }
                },
                clickSelectMember: function (e) {
                    var task = vm.get('task');
                    vm.get("task.ApprovedUsers").push(e.data);
                    vm.get("dsCompanyPersonsAvailableForSecureTasks").remove(e.data);
                },
                clickRemoveSelectedMember: function (e) {
                //if we remove the task assignee from Users With Access we let the user 
                //know that a new Owner should be selected.
                    if (vm.get('task.OwnerID') == e.data.ID) {
                        $.radoloDialog.showAsWindow({
                            "title": "Sorry!",
                            "message": "Task Owner cannot be removed from Users With Access.<br>If you still want to remove then change the Task Owner.<br><br>",
                            "buttons": [{
                                "title": "OK", "callback": function () {
                                    //vm.clickRemovePerson(e);
                                    //vm.get("task.ApprovedUsers").remove(e.data);
                                    //vm.get("dsCompanyPersonsAvailableForSecureTasks").add(e.data);
                                    $.radoloDialog.closeWindow();
                                }
                            }
                            ]
                        });
                    }

                    else
                    {
                        $.radoloDialog.showAsWindow({
                            "title": "Remove Person?",
                            "message": "Are you sure you want to remove this person?",
                            "buttons": [{
                                "title": "OK", "callback": function () {
                                    vm.get("task.ApprovedUsers").remove(e.data);
                                    //Make sure to check the user you are removing exists in the source
                                    //if it is not in source add it
                                    var isUserInAvailableUsers = false;
                                    var availableUsers = vm.get('dsCompanyPersonsAvailableForSecureTasks.data()');
                                    availableUsers.forEach(function (person) {
                                        if (person.ID === e.data.ID) {
                                            isUserInAvailableUsers = true;
                                        }
                                    });

                                    if (!isUserInAvailableUsers) {
                                        vm.get("dsCompanyPersonsAvailableForSecureTasks").add(e.data);
                                    }

                                    $.radoloDialog.closeWindow();
                                }
                            }, {
                                "title": "Cancel", "callback": function () {
                                    $.radoloDialog.closeWindow();
                                }
                            }]
                        });
                    }
                },
                dsCompanyPersonsAvailableForSecureTasks: new kendo.data.DataSource({
                    transport: {
                        read: function (options) {
                            var task = vm.get('task');
                            api.getPersonsAvailableForTaskSecurity(vm.task).done(options.success).fail(options.error);
                        }
                    },
                    schema: {
                        model: { id: "ID" }
                    },
                    sort: { field: "Name", dir: "asc" }
                }),
                clickAddAllMembers: function () {
                    var availableCompanyPersons = vm.get("dsCompanyPersonsAvailableForSecureTasks").data();

                    $.each(availableCompanyPersons, function (index, item) {
                        vm.get("task.ApprovedUsers").push(item);
                    });

                    vm.get("dsCompanyPersonsAvailableForSecureTasks").data([]);
                },
                clickRemoveAllMembers: function () {
                    var selectedCompanyPersons = vm.get("task.ApprovedUsers");

                    $.each(selectedCompanyPersons, function (index, item) {
                        vm.get("dsCompanyPersonsAvailableForSecureTasks").add(item);
                    });

                    vm.set("task.ApprovedUsers", []);
                },
                onFilterMembersKeyup: function (e) {
                    var filter = e.currentTarget.value;
                    vm.dsCompanyPersonsAvailableForSecureTasks.filter({ field: "Name", operator: "contains", value: filter });
                },
                clickAssignMultipleUsers: function () {
                    if (vm.get("assignToAll") || vm.get("assignToAllAdmins")) {
                        vm.set('selectedVisibility', vm.get('visibilityOptions')[0]);
                        vm.set("task.Visibility", "Public");
                        vm.set('isTaskSecurityDisabled', true);
                    } else {
                        vm.set('isTaskSecurityDisabled', false);
                    }
                },
                clickSaveAndNew: function (e) {
                    vm.set('bSaveAndNew', true);
                    vm.clickSaveTask(e);
                },
                clickSaveTask: function (e) {
                    if (vm.get('teamsRemoved') && vm.get("task.Visibility") == "Teams") {
                        $.radoloDialog.showAsWindow({
                            "title": "Teams have been changed!",
                            "message": "Teams may have been removed from security since the original owner was changed. Continue?",
                            "buttons": [{
                                "title": "OK", "callback": function () {
                                    $.radoloDialog.closeWindow();
                                    vm.saveTask(e);
                                }
                            }, {
                                "title": "Cancel", "callback": function () {
                                    $.radoloDialog.closeWindow();
                                }
                            }]
                        });
                    } else {
                        vm.saveTask(e);
                    }
                },
                clickChangeVisibility: function (e) {
                    switch (vm.get('selectedVisibility').value) {
                        case "1":
                            vm.set("task.Visibility", "Public");
                            //vm.set("priority.ApprovedUsers", []);
                            break;
                        case "2":
                              //if the current task's visibility is set to Users then 
                             //make sure to update Approved Users for that task to have both the Assignee and Assigned 
                            vm.dsCompanyPersonsAvailableForSecureTasks.read();

                            vm.set("task.ApprovedUsers", []);
                            var availableAssignees = vm.get('dsTaskAssignees.data()');
                            var isCurrentUserInApprovedUsers = false;
                            $.each(availableAssignees, function (index, item) {
                                vm.get("task.ApprovedUsers").push(item);
                            });
                            availableAssignees.forEach(function (person) {
                                if (person.ID === masterPerson.ID) {
                                    isCurrentUserInApprovedUsers = true;
                                }
                            });
                            if (!isCurrentUserInApprovedUsers) {
                                 vm.get("task.ApprovedUsers").push(vm.get("currentPerson"));                          
                            }
                            vm.set("task.Visibility", "Users");
                            break;
                        case "3":
                            vm.set("task.Visibility", "Teams");
                            break;
                    }
                },

                //VSS
                // Precon Playbook Task Due Date Shift Method
                //added new methoed update by vivek on 04-09-2025
                performTaskDueDateShift: function (task, shiftAllTasks) {
                    console.log('🔄 DEBUG: performTaskDueDateShift called', {
                        taskId: task.ID,
                        shiftAllTasks: shiftAllTasks,
                        endDate: task.EndDate
                    });

                    vm.set("showProgress", true);

                    // Call our new API method
                    Align.Web.Services.SecureAPI.SaveTaskWithDueDateShift(task, shiftAllTasks)
                        .done(function (updatedTasks) {
                            console.log('✅ DEBUG: SaveTaskWithDueDateShift succeeded', {
                                updatedTasksCount: updatedTasks ? updatedTasks.length : 0,
                                updatedTasks: updatedTasks
                            });

                            vm.set("showProgress", false);

                            // Show success message following Align standards
                            if (shiftAllTasks && updatedTasks && updatedTasks.length > 1) {
                                console.log('✅ SUCCESS: Updated ' + updatedTasks.length + ' tasks successfully');
                                // Note: Success message will be shown by parent dialog after refresh
                            }

                            // Publish the EditTaskComplete event to close dialog and refresh parent
                            var savedTask = task;
                            console.log('🔄 DEBUG: Publishing EditTaskComplete event to close dialog');

                            

                            $.pubsub.publish("EditTaskComplete", {
                                SavedTask: savedTask,
                                SaveAndNew: vm.get("bSaveAndNew"),  // Ensure dialog closes and returns to parent
                                HierarchicalUpdate: shiftAllTasks,
                                UpdatedTasksCount: updatedTasks.length
                            }, {
                                Name: "EditTask_HierarchicalUpdate_Completed",
                                DisplayName: "Edit Task - Hierarchical Update - Completed"
                            });
                            
                        })
                        .fail(function (error) {
                            console.error('❌ DEBUG: SaveTaskWithDueDateShift failed', error);

                            vm.set("showProgress", false);
                            var errorMessage = error.responseText || error || "Failed to update task(s)";
                            $.radoloCommon.showError(errorMessage);
                        });
                },


                saveTask: function (e) {
					var task = vm.get('task');
                    var rock = vm.get("rock");
                    var syncCategory = vm.get("SyncCategory");
                    var assignToWho = '';
                    if (vm.get('selectedVisibility').text == "Selected Users") {
                        task.Visibility = "Users";
                    }
                    if (vm.get('selectedVisibility').text == "Selected Teams") {
                        task.Visibility = "Teams";
                        task.Teams = vm.get('selectedTeams');
                    }
                    if (vm.get('selectedVisibility').text == "Everyone") {
                        task.Visibility = "Public";
                    }
                    if (!task.EndDate || task.EndDate == null) {
                        $.radoloDialog.showAsWindow({
                            title: "Whoops",
                            message: "Please select a due date for this task.",
                            buttons: [{ title: "OK" }]
                        });
                        return;
                    }

                    if (!task.ShortDescription) {
                        $.radoloDialog.showAsWindow({
                            title: "Whoops",
                            message: "A Task must have a name.",
                            buttons: [{ title: "OK" }]
                        });
                        return;
                    }

                    if (task.Recurring) {
                        if (task.Recurrences < 0 || task.Frequency == 'never' || task.Frequency == null) {
                            $.radoloDialog.showAsWindow({
                                title: "Whoops",
                                message: "A recurring Task must have a frequency.",
                                buttons: [{ title: "OK" }]
                            });
                            return;
                        }
                    }

                    //VSS
                    // MANDATORY PRIORITY VALIDATION FOR PRECON PLAYBOOK
                    if (masterCompany.AlignBrandID === 6) {
                        if (!rock || typeof rock.ID === "undefined" || rock.ID < 1) {
                            $.radoloDialog.showAsWindow({
                                title: "Whoops",
                                message: "Task must be assigned to a Project or Phase. Select a Project or Phase before saving.",
                                buttons: [{ title: "OK" }]
                            });
                            vm.set("showProgress", false);
                            return;
                        }
                    }

                    if (task.Visibility == "Teams" && (!task.Teams || task.Teams.length <= 0)) {
                        $.radoloDialog.showAsWindow({
                            title: "Whoops",
                            message: "Select one or more Teams before saving.",
                            buttons: [{ title: "OK" }]
                        });
                        return;
                    }

                    //J.S 1-24-22 If this Task will be assigned to all Users then skip this check
                    //if (!task.OwnerID || task.OwnerID < 1) {
                    //    if ((vm.get('assignToAll') || vm.get('assignToAllAdmins')) == false) {
                    //        $.radoloDialog.showAsWindow({
                    //            title: "Whoops",
                    //            message: "A Task must be assigned to at least one person.",
                    //            buttons: [{ title: "OK" }]
                    //        });
                    //        return;
                    //    }
                    //}
                    // Replace the existing validation check with:
                    var hasAssignees = vm.dsTaskAssignees.data().length > 0;
                    var willAssignToAll = vm.get('assignToAll') || vm.get('assignToAllAdmins');

                    if (!task.OwnerID || task.OwnerID < 1) {
                        if (!willAssignToAll && !hasAssignees) {
                            $.radoloDialog.showAsWindow({
                                title: "Whoops",
                                message: "A Task must be assigned to at least one person.",
                                buttons: [{ title: "OK" }]
                            });
                            return;
                        }
                    }

                    if (!rock || typeof rock.ID === "undefined" || rock.ID < 1) {
                        task.RockID = -1;

                        if (vm.get('bIsOKRFeatureEnabled')) {
                            task.RockName = "No OKR Selected";
                        }
                        else {
                            task.RockName = "No Priority Selected";
                        }
                    }

                    if (syncCategory) {
                        if (syncCategory.ID <= 0) {
                            task.SyncCategory = "";
                        }
                        else {
                            task.SyncCategory = syncCategory.Name;
                        }
                    }

                    vm.set("showProgress", true);
                    if (vm.get('assignToAll') || vm.get('assignToAllAdmins')) {
                        let assigneeCount = 0;
                        var defAssignees = vm.getAssignees();

                        defAssignees.done(function (assignees) {
                            assigneeCount = assignees.length;
                            var tasksToSave = [];
                            $.each(assignees, function (index, item) {
                                var tmpTask = $.extend({}, vm.get('task'));
                                tmpTask.Owner = item.Name;
                                tmpTask.OwnerID = item.ID;

                                tasksToSave.push(tmpTask);
                            });


                            if (vm.get('assignToAllAdmins')) {
                                assignToWho = `all ${assigneeCount} admins in the company`;
                            }

                            if (vm.get('assignToAll')) {
                                assignToWho = `all ${assigneeCount} users in the company, including admins`;
                            }

                            $.radoloDialog.showAsWindow({
                                "title": `Assign ${assigneeCount} Tasks?`,
                                "message": "Are you sure you want to assign this task to " + assignToWho + "?",
                                "buttons": [{
                                    "title": "Confirm", "callback": function () {
                                        /*
                                        var defAssignees = vm.getAssignees();
                                        defAssignees.done(function (assignees) {
                                            var tasksToSave = [];
                                            $.each(assignees, function (index, item) {
                                                var tmpTask = $.extend({}, vm.get('task'));
                                                tmpTask.Owner = item.Name;
                                                tmpTask.OwnerID = item.ID;
    
                                                tasksToSave.push(tmpTask);
    
                                            });
                                        */
                                        //2023-07-07 RJK: After the User confirms the creation of multiple tasks, hide the message box window
                                        //to prevent the user from clicking it again. Also, display another message box while the Tasks are being saved
                                        //so that the user knows something is happening.
                                        $.radoloDialog.closeWindow();
                                        $.radoloDialog.showAsWindow({  "title": "Assigning Tasks...",
                                                                        "message": "<p>Assigning Tasks to " + assignToWho + ".</p><p>This may take several seconds.</p>",
                                                                        "buttons": [],
                                                                        "showInProgress": true
                                        });

                                        secureAPI.SaveTaskForMultipleUsers(tasksToSave).done(function (savedTask) {
                                            //VSS 29th Oct 2025 - start - Copy staged documents for all tasks
                                            var stagedDocIDs = vm.get('stagedDocuments') || [];
                                            var isNewTask = !task || !task.ID || task.ID <= 0;

                                            if (isNewTask && stagedDocIDs.length > 0 && savedTask && savedTask.length > 0) {
                                                // Extract task IDs and owner IDs from saved tasks
                                                var taskIDs = savedTask.map(function(t) { return t.ID; });
                                                var taskOwnerIDs = savedTask.map(function(t) { return t.OwnerID; });

                                                // Link documents to all tasks
                                                secureAPI.LinkDocumentsForMultipleTasks(stagedDocIDs, "AlignTasks", taskIDs, taskOwnerIDs)
                                                    .done(function() {
                                                        // Clear staged documents
                                                        vm.set('stagedDocuments', []);
                                                        $.pubsub.publish("EditTaskComplete", { SavedTask: savedTask, SaveAndNew: vm.get("bSaveAndNew") }, { Name: "EditTask_AssignToMultipleUsers_Completed", DisplayName: "Edit Task - Assign to Multiple Users - Completed" });
                                                        $.radoloDialog.closeWindow();
                                                    })
                                                    .fail(function(error) {
                                                        console.error("Failed to link staged documents:", error);
                                                        // Still proceed with task completion even if document linking fails
                                                        $.pubsub.publish("EditTaskComplete", { SavedTask: savedTask, SaveAndNew: vm.get("bSaveAndNew") }, { Name: "EditTask_AssignToMultipleUsers_Completed", DisplayName: "Edit Task - Assign to Multiple Users - Completed" });
                                                        $.radoloDialog.closeWindow();
                                                    });
                                            } else {
                                                // No staged documents, proceed normally
                                                $.pubsub.publish("EditTaskComplete", { SavedTask: savedTask, SaveAndNew: vm.get("bSaveAndNew") }, { Name: "EditTask_AssignToMultipleUsers_Completed", DisplayName: "Edit Task - Assign to Multiple Users - Completed" });
                                                $.radoloDialog.closeWindow();
                                            }
                                            //VSS 29th Oct 2025 - end
                                        }).fail(function (e) {
                                            vm.set("showProgress", false);
                                            $.radoloCommon.showError(e);
                                        });

                                        //});

                                    }
                                }, {
                                    "title": "Cancel", "callback": function () {
                                        $.radoloDialog.closeWindow();
                                        vm.set("showProgress", false);
                                    }
                                }]
                            });

                        });
                    }
                    else if (vm.dsTaskAssignees.data().length > 0) {
                        var defAssignees = vm.getAssignees();

                        defAssignees.done(function (assignees) {
                            var tasksToSave = [];
                            $.each(assignees, function (index, item) {
                                var tmpTask = $.extend({}, vm.get('task'));
                                tmpTask.Owner = item.Name;
                                tmpTask.OwnerID = item.ID;

                                tasksToSave.push(tmpTask);

                            });
                            secureAPI.SaveTaskForMultipleUsers(tasksToSave).done(function (savedTask) {
                                //VSS 29th Oct 2025 - start - Copy staged documents for all tasks
                                var stagedDocIDs = vm.get('stagedDocuments') || [];
                                var isNewTask = !task || !task.ID || task.ID <= 0;

                                let trackInfo = {
                                    Name: "EditTask_",
                                    DisplayName: "Edit Task - "
                                };

                                if (tasksToSave.length > 1) {
                                    trackInfo.Name += "AssignToMultipleUsers_Completed"
                                    trackInfo.DisplayName += "Assign to Multiple Users - Completed"
                                }
                                else {
                                    trackInfo.Name += "SingleTask_Completed"
                                    trackInfo.DisplayName += "Single Task - Completed"
                                }

                                if (isNewTask && stagedDocIDs.length > 0 && savedTask && savedTask.length > 0) {
                                    // Extract task IDs and owner IDs from saved tasks
                                    var taskIDs = savedTask.map(function(t) { return t.ID; });
                                    var taskOwnerIDs = savedTask.map(function(t) { return t.OwnerID; });

                                    // Link documents to all tasks
                                    secureAPI.LinkDocumentsForMultipleTasks(stagedDocIDs, "AlignTasks", taskIDs, taskOwnerIDs)
                                        .done(function() {
                                            // Clear staged documents
                                            vm.set('stagedDocuments', []);
                                            $.pubsub.publish("EditTaskComplete", { SavedTask: savedTask, SaveAndNew: vm.get("bSaveAndNew") }, trackInfo);
                                        })
                                        .fail(function(error) {
                                            console.error("Failed to link staged documents:", error);
                                            // Still proceed with task completion even if document linking fails
                                            $.pubsub.publish("EditTaskComplete", { SavedTask: savedTask, SaveAndNew: vm.get("bSaveAndNew") }, trackInfo);
                                        });
                                } else {
                                    // No staged documents, proceed normally
                                    $.pubsub.publish("EditTaskComplete", { SavedTask: savedTask, SaveAndNew: vm.get("bSaveAndNew") }, trackInfo);
                                }
                                //VSS 29th Oct 2025 - end
                            }).fail(function (e) {
                                vm.set("showProgress", false);
                                $.radoloCommon.showError(e);
                            });

                        });
                    }
                    else {
                        //2023-11-27 ND: Add logic to handle linked task behavior
                        var isDirty = kendo.stringify(taskBeforeEdit) !== kendo.stringify(task);
                        var isPriorityChanged = task.RockID !== taskBeforeEdit.RockID;
                        var isHuddleChanged = task.HuddleID !== taskBeforeEdit.HuddleID;
                        var isAssociationChanged = isPriorityChanged || isHuddleChanged;
                        var getLinkedTaskIfNecessary = $.Deferred();

                        if (vm.get('bIsPeriodTransitionEnabled') && isDirty) {
                            secureAPI.GetLinkedTask(task.ID).done(getLinkedTaskIfNecessary.resolve).fail(getLinkedTaskIfNecessary.reject);
                        } else {
                            getLinkedTaskIfNecessary.resolve(null);
                        }

                        getLinkedTaskIfNecessary.done((linkedTask) => {

                            let promptLinkedTaskActionIfNecessary = $.Deferred();
                            let isOriginalTask = linkedTask && linkedTask.ClonedAlignTaskID === -1;
                            if (linkedTask) {
                                let rockType = this.get('bIsOKRFeatureEnabled') ? 'OKR' : 'Priority'

                                let linkedTaskState = "Future";

                                if (!isOriginalTask) {
                                    linkedTaskState = "Original";
                                }

                                let associationDialogMsg = isOriginalTask ?
                                    `<p>This Task is associated with the <strong>${linkedTask.RockName}</strong> ${rockType} that was copied over to next Period.</p><p>What do you want to do to the <strong>${linkedTaskState}</strong> Task?</p>` :
                                    `<p>This Task was copied from a Task in the current Period associated with <strong>${linkedTask.RockName}</strong> ${rockType}.</p><p>What do you want to do to the <strong>${linkedTaskState}</strong> Task?</p>`;

                                var associationChanged = isPriorityChanged ? 'Priority' : 'Huddle';
                                var associationAction = 'Remove';
                                if (associationChanged === 'Priority') {
                                    if (task.RockID !== -1) {
                                        associationAction = 'Add'
                                    }
                                } else {
                                    if (task.HuddleID !== -1) {
                                        associationAction = 'Add'
                                    }
                                }

                                var associationChangedDialog = {
                                    title: "Update Associated Task?",
                                    message: associationDialogMsg,
                                    buttons: [{
                                        title: "Delete " + linkedTaskState + " Task",
                                        callback: () => {
                                            promptLinkedTaskActionIfNecessary.resolve("delete");
                                            $.radoloDialog.closeWindow();
                                        }
                                    }, {
                                        title: `${associationAction} connection to ${associationChanged}`,
                                        callback: () => {
                                            promptLinkedTaskActionIfNecessary.resolve("update");
                                            $.radoloDialog.closeWindow();
                                        }
                                    }, {
                                        title: "Separate " + linkedTaskState + " Task",
                                        callback: () => {
                                            promptLinkedTaskActionIfNecessary.resolve("separate");
                                            $.radoloDialog.closeWindow();
                                        }
                                    }]
                                }

                                let otherInformationDialogMsg = isOriginalTask ?
                                    `This Task is associated with the <strong>${linkedTask.RockName}</strong> ${rockType} that was copied over to next Period. Do you want to update that Task too?` :
                                    `This Task was copied from a task in the current Period associated with <strong>${linkedTask.RockName}</strong> ${rockType}. Do you want to update that Task too?`;

                                var otherInformationChangedDialog = {
                                    title: "Update Associated Task?",
                                    message: otherInformationDialogMsg,
                                    buttons: [{
                                        title: "Yes",
                                        callback: () => {
                                            promptLinkedTaskActionIfNecessary.resolve("update");
                                            $.radoloDialog.closeWindow();
                                        }
                                    }, {
                                        title: `No`,
                                        callback: () => {
                                            promptLinkedTaskActionIfNecessary.resolve("separate");
                                            $.radoloDialog.closeWindow();
                                        }
                                    }]
                                }

                                var dialog = isAssociationChanged ? associationChangedDialog : otherInformationChangedDialog;
                                $.radoloDialog.showAsWindow(dialog);

                            } else {
                                promptLinkedTaskActionIfNecessary.resolve('');
                            }

                            //VSS
                            // update by vivek on 04-09-2025
                            promptLinkedTaskActionIfNecessary.done((action) => {
                                // Check for Precon Playbook due date shift before saving
                                if (masterCompany.AlignBrandID === 6 && task.EndDate !== vm.get('originalEndDate')) {
                                    console.log('🎯 DEBUG: Due date changed in Precon Playbook - showing confirmation dialog');

                                    var originalEndDate = vm.get('originalEndDate');
                                    var taskEndDate = task.EndDate;

                                    console.log('🔍 DEBUG: Date comparison details', {
                                        originalEndDate: originalEndDate,
                                        taskEndDate: taskEndDate,
                                        originalType: typeof originalEndDate,
                                        taskType: typeof taskEndDate
                                    });

                                    // Function to parse .NET JSON date format /Date(1809129600000)/
                                    //added new methoed to update the all the task related to the project update by vivek on 04-09-2025
                                    function parseNetDate(dateValue) {
                                        if (!dateValue) return null;

                                        // Handle string dates in .NET JSON format
                                        if (typeof dateValue === 'string' && dateValue.indexOf('/Date(') === 0) {
                                            var timestamp = parseInt(dateValue.match(/\/Date\((\d+)\)\//)[1], 10);
                                            return new Date(timestamp);
                                        }

                                        // Handle DateOnly objects with ValueDate
                                        if (dateValue && dateValue.ValueDate) {
                                            return new Date(dateValue.ValueDate);
                                        }

                                        // Handle regular Date objects or date strings
                                        if (dateValue) {
                                            return new Date(dateValue);
                                        }

                                        return null;
                                    }

                                    // Parse both dates using the helper function
                                    var originalDate = parseNetDate(originalEndDate);
                                    var newDate = parseNetDate(taskEndDate);

                                    if (!originalDate || !newDate) {
                                        console.error('❌ DEBUG: Failed to parse one or both dates', {
                                            originalDate: originalDate,
                                            newDate: newDate,
                                            originalEndDate: originalEndDate,
                                            taskEndDate: taskEndDate
                                        });
                                        return; // Exit early if we can't parse the dates
                                    }

                                    console.log('🔍 DEBUG: Parsed dates', {
                                        originalDate: originalDate,
                                        newDate: newDate,
                                        originalValid: !isNaN(originalDate.getTime()),
                                        newValid: !isNaN(newDate.getTime())
                                    });

                                    var daysDiff = 0;
                                    if (!isNaN(originalDate.getTime()) && !isNaN(newDate.getTime())) {
                                        daysDiff = Math.ceil((newDate - originalDate) / (1000 * 60 * 60 * 24));
                                    } else {
                                        console.error('❌ DEBUG: Invalid date calculation - one or both dates are invalid');
                                        daysDiff = 0;
                                    }
                                    var direction = daysDiff > 0 ? "forward" : "backward";
                                    var absDays = Math.abs(daysDiff);

                                    vm.set("showProgress", false);
                                    // Show confirmation modal with X close button
                                    var confirmDialog = $.radoloDialog.showAsWindow({
                                        "title": "Shift All Related Tasks?",
                                        "message": "<p>You are moving the '" + task.ShortDescription + "' task " + absDays + " day(s) " + direction + ".</p><p>Do you want to shift ALL open tasks in the " + rock.Teams[0]?.Name +" "+ direction + " ?</p>",
                                        //"message": "Moving this task's due date " + absDays + " day(s) " + direction + ". Do you want to shift ALL open tasks in this priority and its sub-priorities by the same amount?",
                                        "showClose": ["Close"],  // Enable X close button
                                        "buttons": [
                                            {
                                                "title": "Yes, shift all project tasks",
                                                "callback": function () {
                                                    console.log('✅ DEBUG: User selected YES - shifting all tasks');
                                                    
                                                    vm.performTaskDueDateShift(task, true);
                                                    $.radoloDialog.closeWindow();
                                                }
                                            },
                                            {
                                                "title": "No, only this task",
                                                "callback": function () {
                                                    console.log('📝 DEBUG: User selected NO - updating only this task');
                                                    
                                                    vm.performTaskDueDateShift(task, false);
                                                    $.radoloDialog.closeWindow();
                                                }
                                            }
                                        ]
                                    });

                                    // Add close event handler to reset showProgress when X is clicked
                                    // Following Align.Common.ts pattern for handling close events
                                    //confirmDialog.bind("deactivate", function () {
                                    //    console.log('🔄 DEBUG: Dialog closed via X button - resetting showProgress');
                                    //    vm.set("showProgress", false);
                                    //});

                                    return; // Exit early, don't continue with normal save
                                }

                                // Normal save flow for non-Precon or no date change
                                secureAPI.SaveTask(task).done(function (savedTask) {
                                    switch (action) {
                                        case 'delete': {
                                            let taskWithLinkInformation = isOriginalTask ? task : linkedTask;
                                            let clonedTask = isOriginalTask ? linkedTask : task;
                                            // we need to remove the reference on the original Task before deleting.
                                            taskWithLinkInformation.ClonedAlignTaskID = -1;
                                            secureAPI.SaveTask(taskWithLinkInformation).fail($.radoloCommon.showError).done(() => {
                                                secureAPI.DeleteTask(clonedTask.ID).fail($.radoloCommon.showError);
                                            });
                                            break;
                                        }

                                        case 'update': {
                                            var clonedTaskWithUpdates = Align.Common.deepCopy(task);
                                            clonedTaskWithUpdates.ID = linkedTask.ID;
                                            clonedTaskWithUpdates.ClonedAlignTaskID = linkedTask.ClonedAlignTaskID;

                                            if (!isHuddleChanged) {
                                                clonedTaskWithUpdates.HuddleID = linkedTask.HuddleID;
                                            }

                                            if (!isPriorityChanged) {
                                                clonedTaskWithUpdates.RockID = linkedTask.RockID;
                                            }

                                            secureAPI.SaveTask(clonedTaskWithUpdates).fail($.radoloCommon.showError);
                                            break;
                                        }

                                        case 'separate': {
                                            let taskWithLinkInformation = isOriginalTask ? task : linkedTask;
                                            taskWithLinkInformation.ClonedAlignTaskID = -1;
                                            secureAPI.SaveTask(taskWithLinkInformation).fail($.radoloCommon.showError);
                                            break;
                                        }
                                        default:
                                            break;
                                    }

                                    //VSS 29th Oct 2025 - start - Check if there are staged documents to link
                                    var stagedDocIDs = vm.get('stagedDocuments') || [];
                                    var isNewTask = !task || !task.ID || task.ID <= 0;

                                    if (isNewTask && stagedDocIDs.length > 0 && savedTask && savedTask.ID > 0) {
                                        // Update document source for all staged documents
                                        secureAPI.UpdateDocumentSource(stagedDocIDs, "AlignTasks", savedTask.ID)
                                            .done(function() {
                                                // Clear staged documents
                                                vm.set('stagedDocuments', []);
                                            })
                                            .fail(function(error) {
                                                console.error("Failed to link staged documents:", error);
                                            });
                                    }
                                    //VSS 29th Oct 2025 - end

                                    $.pubsub.publish("EditTaskComplete", { SavedTask: savedTask, SaveAndNew: vm.get("bSaveAndNew") }, { Name: "EditTask_SingleTask_Completed", DisplayName: "Edit Task - Single Task - Completed" });
                                    vm.set("showProgress", false);
                                }).fail(function (e) {
                                    vm.set("showProgress", false);
                                    $.radoloCommon.showError(e);
                                });
                            });
                        });
                    }
                },
                setAvailableVisibilityOptions: function (task) {
                    if (vm.get("IsNewTask") && vm.get('task.RockID') > 1) {
                        vm.set('isTaskSecurityDisabled', true);
                    }
                },
                getVisibilityOptions: function () {
                    if (vm.get('bIsHuddlesFeatureEnabled')) {
                    return [
                        { id: 1, text: "Everyone", value: "1", isDisabled: false },
                        { id: 2, text: "Selected Users", value: "2", isDisabled: false },
                        { id: 3, text: "Selected Teams", value: "3", isDisabled: false }
                    ];
                    
                    } else {
                        return [
                            { id: 1, text: "None", value: "1", isDisabled: false },
                            { id: 2, text: "Selected Users", value: "2", isDisabled: false },
                        ];
                    }
                },
                taskHasPriorityLink: false,
                setVisibilityFromPriority: function (task) {
                    if (task.RockID > 1) {
                        var priority = {
                            ID: task.RockID,
                            Name: task.RockName,
                            Visibility: task.RockVisibility,
                            Teams: task.RockTeams,
                            ApprovedUsers: task.RockApprovedUsers,
                            Tags: task.RockTags || []
                        }

                        vm.set("rock", priority);

                        //set dropdown to readonly
                        vm.onSelectPriority();
                        vm.onSelectOKR();
                        vm.onSelectProjectPhase();

                        if (priority.Visibility == "Teams") {
                            //Selected Visibility set to Teams.
                            vm.set('selectedVisibility', vm.get('visibilityOptions')[2]);
                            task.Visibility = "Teams";
                            task.Teams = priority.Teams;
                            task.ApprovedUsers = priority.ApprovedUsers;
                            vm.set('task.Visibility', "Teams");
                            vm.set('task.Teams', priority.Teams);
                            vm.set('task.ApprovedUsers', priority.ApprovedUsers);
                            vm.set("selectedTeams", task.Teams);
                        }
                        else if (priority.Visibility == "Users") {
                            task.Visibility = "Users";
                            task.ApprovedUsers = priority.ApprovedUsers;
                            vm.set('task.Visibility', "Users");
                            vm.set('task.ApprovedUsers', priority.ApprovedUsers);
                            vm.dsCompanyPersonsAvailableForSecureTasks.read();
                        }
                        else { //Everyone 
                            vm.set('selectedVisibility', vm.get('visibilityOptions')[0]);
                            task.Visibility = "Everyone";
                            task.ApprovedUsers = priority.ApprovedUsers;
                            vm.set('task.Visibility', "Everyone");
                            vm.set('task.ApprovedUsers', priority.ApprovedUsers);
                        }

                        vm.set('isTaskSecurityDisabled', true);
                        vm.set('taskHasPriorityLink', true);
                    }

                    else if (task.RockID === -1 && vm.get("IsNewTask")) {
                        // setting from Manage Priorities. We know the task is going to be associated with a priority that doesn't exist yet, don't get the user the option to change the priority
                        vm.set("isUnsavedPriority", true);
                        var priority = {
                            Name: task.RockName,
                            Visibility: task.RockVisibility,
                            Teams: task.RockTeams,
                            ApprovedUsers: task.RockApprovedUsers
                        }

                        vm.set("rock", priority);

                        if (vm.get('IsNewTask')) {
                            if (priority.Visibility == "Teams") {
                                //Selected Visibility set to Teams.
                                vm.set('selectedVisibility', vm.get('visibilityOptions')[2]);

                                task.Visibility = "Teams";
                                task.Teams = priority.Teams;
                                task.ApprovedUsers = priority.ApprovedUsers;
                                vm.set('task.Visibility', "Teams");
                                vm.set('task.Teams', priority.Teams);
                                vm.set('task.ApprovedUsers', priority.ApprovedUsers);   
                                vm.set("selectedTeams", task.Teams);
                            }
                            else if (priority.Visibility == "Users") {
                                task.Visibility = "Users";
                                task.ApprovedUsers = priority.ApprovedUsers;
                                vm.set('task.Visibility', "Users");                          
                                vm.set('task.ApprovedUsers', priority.ApprovedUsers);
                            }
                        }

                        vm.set('isTaskSecurityDisabled', true);
                        vm.set('taskHasPriorityLink', true);
                    }
                    if (task.Owner && task.OwnerID > 1 && !vm.get("IsNewTask")) {
                        var person = { ID: task.OwnerID, Name: task.Owner };
                        vm.set("owner", person);

                        // set dropdown to readonly
                        $("#taskOwner")[0].setAttribute('readonly', true);
                    }
                    if (!masterPerson.IsAdministrator && masterPerson.ID != task.OwnerID) {
                        vm.set('isTaskSecurityDisabled', true);
                    }
                    if (task.Visibility == "Public") {
                        vm.set('selectedVisibility', vm.get('visibilityOptions')[0]);
                    }
                    else if (task.Visibility == "Users") {
                        vm.set('selectedVisibility', vm.get('visibilityOptions')[1]);
                        vm.set('task.Visibility', "Users");                          
                        vm.set('task.ApprovedUsers', task.ApprovedUsers);
                    }
                    else if (task.Visibility == "Teams") {
                        vm.set('selectedVisibility', vm.get('visibilityOptions')[2]);
                        vm.set('selectedTeams', task.Teams);
                        vm.set('task.Visibility', "Teams");
                        vm.set('task.Teams', task.Teams);
                        vm.set('task.ApprovedUsers', task.ApprovedUsers);
                    }

                    vm.setAvailableVisibilityOptions(task);
                },
                isTeamsVisible: function () {
                    return (vm.get('selectedVisibility.id') == 3 && !vm.get('isTaskSecurityDisabled'));
                },
                isUsersVisible: function () {
                    return (vm.get('selectedVisibility.id') == 2 && !vm.get('isTaskSecurityDisabled'));
                },
                getAssignees: function () {
                    var assignToAll = vm.get('assignToAll');
                    var assignToAllAdmins = vm.get('assignToAllAdmins');
                    var taskAssignees = vm.get('dsTaskAssignees.data()');

                    if (assignToAll || assignToAllAdmins) {
                        return secureAPI.GetPersons().then(function (Person) {

                            var assignees = [];

                            if (assignToAll) {
                                $.each(Person.Data, function (index, item) {
                                    var assignee = item;
                                    assignees.push(assignee);
                                });
                            }
                            else if (assignToAllAdmins) {
                                $.each(Person.Data, function (index, item) {
                                    if (item.IsAdministrator) {
                                        assignees.push(item);
                                    }
                                });
                            }

                            return assignees;
                        });

                    }
                    else {

                        var assignees = [];

                        var def = $.Deferred();

                        $.each(taskAssignees, function (index, item) {
                            assignees.push(item);
                        });

                        return def.resolve(assignees);
                    }
                },
                //VSS 29th Oct 2025 - start - Task Documents event handlers
                clickAddDocument: function (e) {
                    e.preventDefault();

                    var options = Align.Common.DrawerOptions.defaultNarrowDrawer;
                    options.url = "/Application/UploadDocument.aspx";
                    options.slideDirection = "left";
                    var d = new radolo.drawer(options);

                    var task = vm.get('task');
                    var isNewTask = !task || !task.ID || task.ID <= 0;

                    // VSS 29th Oct 2025 - Collect tags from Priority and Huddle
                    var allTags = [];
                    var tagsMap = {}; // To avoid duplicates

                    // Get tags from Priority (Rock) if assigned
                    var rock = vm.get('rock');
                    if (rock && rock.Tags && rock.Tags.length > 0) {
                        rock.Tags.forEach(function(tag) {
                            if (!tagsMap[tag.ID]) {
                                tagsMap[tag.ID] = tag;
                                allTags.push(tag);
                            }
                        });
                    }

                    // Get tags from Huddle if assigned
                    var selectedHuddle = vm.get('selectedHuddle');
                    if (selectedHuddle && selectedHuddle.Tags && selectedHuddle.Tags.length > 0) {
                        selectedHuddle.Tags.forEach(function(tag) {
                            if (!tagsMap[tag.ID]) {
                                tagsMap[tag.ID] = tag;
                                allTags.push(tag);
                            }
                        });
                    }

                    var newDocument = {
                        ID: -1,
                        DocumentName: '',
                        Description: '',
                        DocumentType: 'File',
                        DocumentUrl: '',
                        Tags: allTags, // VSS 29th Oct 2025 - Pre-populate with tags from Priority and Huddle
                        DocumentSourceEntity: 'AlignTasks',
                        DocumentSourceID: isNewTask ? -1 : task.ID,
                        CurrentEntityID: isNewTask ? -1 : task.ID, // VSS 07th Nov 2025 - Same as DocumentSourceID for add document
                        CurrentEntity: 'AlignTasks', // VSS 11th Nov 2025 - Current entity type being edited from
                        CompanyBrandID: masterCompany.AlignBrandID
                    };

                    $.pubsub.subscribeSingle("NeedDocument", function () {
                        $.pubsub.publish("DocumentReady", newDocument);
                    });

                    $.pubsub.subscribeSingle("UploadDocumentComplete", function (result) {
                        d.close();
                        if (result && result.success) {
                            if (isNewTask && result.documentInfo) {
                                // For new tasks, stage the document ID
                                var stagedDocs = vm.get('stagedDocuments') || [];
                                stagedDocs.push(result.documentInfo.ID);
                                vm.set('stagedDocuments', stagedDocs);

                                // Refresh the display to show staged documents
                                vm.get('dsTaskDocuments').read();
                            } else {
                                // For existing tasks, reload documents normally
                                vm.get('dsTaskDocuments').read();
                            }
                        }
                    });

                    d.open();
                },

                clickEditTaskDocument: function (e) {
                    e.preventDefault();
                    e.stopPropagation();

                    var $item = $(e.currentTarget).closest('.task-document-item');
                    var documentId = $item.data('document-id');
                    var documents = vm.get('dsTaskDocuments').data();
                    var doc = documents.find(function(d) { return d.ID === documentId; });

                    if (!doc) {
                        console.error("Document not found");
                        return;
                    }

                    // Add CompanyBrandID to the document for Precon validation
                    doc.CompanyBrandID = masterCompany.AlignBrandID;
                    // VSS 07th Nov 2025 - Add current Task ID for filtering shared document list
                    var currentTask = vm.get('task');
                    doc.CurrentEntityID = currentTask ? currentTask.ID : 0;
                    // VSS 11th Nov 2025 - Add current entity type for disconnect button logic
                    doc.CurrentEntity = 'AlignTasks';

                    var options = Align.Common.DrawerOptions.defaultNarrowDrawer;
                    options.url = "/Application/UploadDocument.aspx";
                    options.slideDirection = "left";
                    var d = new radolo.drawer(options);

                    $.pubsub.subscribeSingle("NeedDocument", function () {
                        $.pubsub.publish("DocumentReady", doc);
                    });

                    $.pubsub.subscribeSingle("UploadDocumentComplete", function (result) {
                        if (result && result.success) {
                            vm.get('dsTaskDocuments').read();
                        }
                        d.close();
                    });

                    d.open();
                },

                clickDeleteTaskDocument: function (e) {
                    e.preventDefault();
                    e.stopPropagation();

                    var $item = $(e.currentTarget).closest('.task-document-item');
                    var documentId = $item.data('document-id');
                    var documents = vm.get('dsTaskDocuments').data();
                    var doc = documents.find(function(d) { return d.ID === documentId; });

                    if (!doc) {
                        console.error("Document not found");
                        return;
                    }

                    // VSS 07th Nov 2025 - Check if document is shared before deleting
                    secureAPI.GetDocumentSharedStatus(doc.ID)
                        .done(function (sharedStatus) {
                            // Filter out current location
                            var currentTask = vm.get('task');
                            var currentTaskID = currentTask ? currentTask.ID : 0;

                            var filteredLinks = sharedStatus.Links.filter(function (link) {
                                if (currentTaskID > 0) {
                                    return !(link.Entity === 'AlignTasks' && link.RecordID === currentTaskID);
                                }
                                return true;
                            });

                            var deleteMessage = '';
                            var currentTask = vm.get('task');
                            var isNewTask = !currentTask || !currentTask.ID || currentTask.ID <= 0;
                            var showDisconnectButton = !isNewTask && filteredLinks.length > 0;

                            if (filteredLinks.length > 0) {
                                // Document is connected to other locations
                                var linksList = '<ul style="margin: 10px 0; padding-left: 20px;">';
                                filteredLinks.forEach(function (link) {
                                    var entityLabel = link.Entity === 'AlignTasks' ? 'Task' :
                                                     link.Entity === 'Rocks' ? 'Priority' :
                                                     link.Entity === 'HuddleGroups' || link.Entity === 'WeeklyHuddleGroups' ? 'Huddle' : link.Entity;
                                    var ownerPart = link.OwnerName ? link.OwnerName + '\'s ' : '';
                                    linksList += '<li>' + ownerPart + entityLabel + ' : ' + link.EntityName + '</li>';
                                });
                                linksList += '</ul>';

                                deleteMessage = '<div style="text-align: left;">' +
                                    '<p>The document <strong>' + doc.DocumentName + '</strong> is currently connected to <strong>' + filteredLinks.length + '</strong> other location(s):</p>' +
                                    linksList +
                                    '<div style="background-color: #fff3cd; border-left: 3px solid #ffc107; padding: 6px 10px; margin: 8px;">' +
                                    '<div style="text-align: center; font-weight: bold; margin-bottom: 4px;">Note:</div>' +
                                    '<div style="margin-bottom: 2px;"><strong>Delete Permanently:</strong> Permanently delete the document from the system and remove all the connections.</div>' +
                                    (showDisconnectButton ? '<div><strong>Disconnect:</strong> Just disconnect from this task.</div>' : '') +
                                    '</div>' +
                                    '</div>';
                            } else {
                                // Document is not connected to other locations
                                deleteMessage = '<p>Are you sure you want to delete "<strong>' + doc.DocumentName + '</strong>"?</p><p><em class="small">This will permanently delete the document from the system.</em></p>';
                            }

                            // VSS 11th Nov 2025 - Build buttons array based on context
                            var dialogButtons = [
                                {
                                    title: "Delete Permanently",
                                    callback: function () {
                                        vm.set('showProgress', true);

                                        secureAPI.DeleteDocument(doc.ID)
                                            .done(function () {
                                                vm.set('showProgress', false);

                                                // If it's a staged document, remove it from stagedDocuments array
                                                var stagedDocs = vm.get('stagedDocuments') || [];
                                                var index = stagedDocs.indexOf(doc.ID);
                                                if (index > -1) {
                                                    stagedDocs.splice(index, 1);
                                                    vm.set('stagedDocuments', stagedDocs);
                                                }

                                                vm.get('dsTaskDocuments').read();
                                                $.radoloDialog.closeWindow();
                                            })
                                            .fail(function (error) {
                                                vm.set('showProgress', false);
                                                console.error("Failed to delete document:", error);
                                                $.radoloCommon.showError(error);
                                                $.radoloDialog.closeWindow();
                                            });
                                    }
                                }
                            ];

                            // Add "Disconnect" button only for existing tasks with shared documents
                            if (showDisconnectButton) {
                                dialogButtons.push({
                                    title: "Disconnect",
                                    callback: function () {
                                        vm.set('showProgress', true);
                                        var currentTaskID = currentTask ? currentTask.ID : 0;

                                        var delinkData = {
                                            DocumentID: doc.ID,
                                            Entity: 'AlignTasks',
                                            EntityID: currentTaskID
                                        };

                                        secureAPI.DelinkDocumentFromEntity(delinkData)
                                            .done(function () {
                                                vm.set('showProgress', false);

                                                // If it's a staged document, remove it from stagedDocuments array
                                                var stagedDocs = vm.get('stagedDocuments') || [];
                                                var index = stagedDocs.indexOf(doc.ID);
                                                if (index > -1) {
                                                    stagedDocs.splice(index, 1);
                                                    vm.set('stagedDocuments', stagedDocs);
                                                }

                                                vm.get('dsTaskDocuments').read();
                                                $.radoloDialog.closeWindow();
                                            })
                                            .fail(function (error) {
                                                vm.set('showProgress', false);
                                                console.error("Failed to disconnect document:", error);
                                                $.radoloCommon.showError(error);
                                                $.radoloDialog.closeWindow();
                                            });
                                    }
                                });
                            }

                            // Always add "Cancel" button
                            dialogButtons.push({
                                title: "Cancel",
                                classes: "button btn-ghost",
                                callback: function () {
                                    $.radoloDialog.closeWindow();
                                }
                            });

                            $.radoloDialog.showAsWindow({
                                title: 'Delete Document?',
                                message: deleteMessage,
                                buttons: dialogButtons
                            });
                        })
                        .fail(function (error) {
                            console.error("Error checking document shared status:", error);
                            // If API fails, show basic delete confirmation
                            $.radoloDialog.showAsWindow({
                                title: 'Delete Document?',
                                message: '<p>Are you sure you want to delete "<strong>' + doc.DocumentName + '</strong>"?</p><p><em class="small">This will permanently delete the document from the system.</em></p>',
                                buttons: [
                                    {
                                        title: "Delete",
                                        callback: function () {
                                            vm.set('showProgress', true);

                                            secureAPI.DeleteDocument(doc.ID)
                                                .done(function () {
                                                    vm.set('showProgress', false);
                                                    var stagedDocs = vm.get('stagedDocuments') || [];
                                                    var index = stagedDocs.indexOf(doc.ID);
                                                    if (index > -1) {
                                                        stagedDocs.splice(index, 1);
                                                        vm.set('stagedDocuments', stagedDocs);
                                                    }
                                                    vm.get('dsTaskDocuments').read();
                                                    $.radoloDialog.closeWindow();
                                                })
                                                .fail(function (error) {
                                                    vm.set('showProgress', false);
                                                    console.error("Failed to delete document:", error);
                                                    $.radoloCommon.showError(error);
                                                    $.radoloDialog.closeWindow();
                                                });
                                        }
                                    },
                                    {
                                        title: "Cancel",
                                        classes: "button btn-ghost",
                                        callback: function () {
                                            $.radoloDialog.closeWindow();
                                        }
                                    }
                                ]
                            });
                        });
                },

                clickViewTaskDocument: function (e) {
                    e.preventDefault();
                    var doc = e.data;
                    console.log("View Task Document:", doc);

                    if (!doc) {
                        console.error("No document data found");
                        return;
                    }

                    if (doc.DocumentType === "File") {
                        secureAPI.ViewDocument(doc.ID).done(function (presignedUrl) {
                            var link = document.createElement('a');
                            link.href = presignedUrl;
                            link.download = doc.OriginalFileName || 'download';
                            document.body.appendChild(link);
                            link.click();
                            document.body.removeChild(link);
                        }).fail(function (error) {
                            console.error("Failed to get presigned URL:", error);
                            $.radoloCommon.showError(error);
                        });
                    } else if (doc.DocumentType === "Link" && doc.DocumentUrl) {
                        window.open(doc.DocumentUrl, '_blank');
                    } else {
                        console.error("Invalid document type or missing URL");
                        $.radoloCommon.showError("Cannot open document");
                    }
                },
                //VSS 29th Oct 2025 - end - Task Documents event handlers

                clickCloseDialog: function (e) {
                    e.preventDefault();
                    vm.cleanupChangeLogs();
                    var tasksSaved = false;

                    let trackInfo = {
                        Name: "EditTask_",
                        DisplayName: "Edit Task - "
                    };

                    if (vm.get("tasksSaved")) {
                        tasksSaved = true;

                        trackInfo.Name += "TasksUpdated";
                        trackInfo.DisplayName += " - Tasks Updated";
                    }
                    else {
                        trackInfo.Name += "Cancelled";
                        trackInfo.DisplayName += "Cancelled by User";
                    }

                    //VSS 29th Oct 2025 - start - Check if there are staged documents that need to be cleaned up
                    var stagedDocIDs = vm.get('stagedDocuments') || [];
                    var task = vm.get('task');
                    var isNewTask = !task || !task.ID || task.ID <= 0;

                    if (isNewTask && stagedDocIDs.length > 0) {
                        // Delete staged documents before closing using bulk delete
                        secureAPI.DeleteDocuments(stagedDocIDs)
                            .always(function() {
                                // Clear staged documents array
                                vm.set('stagedDocuments', []);
                                $.pubsub.publish("EditTaskComplete", tasksSaved, trackInfo);
                            });
                        return;
                    }
                    //VSS 29th Oct 2025 - end

                    $.pubsub.publish("EditTaskComplete", tasksSaved, trackInfo);
                },
                dsTaskAssignees: new kendo.data.DataSource({
                    data: [],
                    schema: {
                        model: { id: "ID" }
                    },
                }),
                dsSearchPersons: new kendo.data.DataSource({
                    transport: {
                        read: function (options) {
                            var filter = "";
                            //Added more requirements so that the search bar doesnt crash when empty - J.S. 7/19/18
                            if (options.data.filter != undefined
                                && options.data.filter.filters != undefined
                                && options.data.filter.filters.length != undefined
                                && options.data.filter.filters.length > 0) {

                                filter = options.data.filter.filters[0].value
                            }
                            secureAPI.SearchForPersons(filter).done(options.success).fail(options.error);
                        }
                    },
                    schema: {
                        model: { id: "ID" }
                    },
                    batch: false,
                    sort: { field: "Name", dir: "asc" }

                }),
                dsPriorities: new kendo.data.DataSource({
                    transport: {
                        read: function (options) {

                            var filter = "";

                            //Added more requirements so that the search bar doesnt crash when empty - J.S. 7/19/18
                            if (options.data.filter != undefined
                                && options.data.filter.filters != undefined
                                && options.data.filter.filters.length != undefined
                                && options.data.filter.filters.length > 0) {

                                filter = options.data.filter.filters[0].value;
                            }
                            //2020-09-27 RJK: Default to the current Date but, if the Task is available and has a Due Date (EndDate), use it.
                            var searchDate = new Date();

                            if (vm && vm.get('task.EndDate')) {
                                searchDate = vm.get('task.EndDate');
                            }
                            secureAPI.GetRockSearchItems(filter, searchDate, 0, true).done(function (results) {
                                options.success(results);

                            }).fail(options.error);
                        }
                    },
                    schema: {
                        model: { id: "ID" }
                    },
                    serverPaging: true,
                    serverFiltering: true,
                    serverSorting: true
                }),
                dsUserTeams: new kendo.data.DataSource({
                    transport: {
                        read: function (options) {
                            if (vm.get('task.OwnerID') > 1) {
                                secureAPI.GetAlignTeamsByPerson(vm.get('task.OwnerID')).done(function (results) {
                                    var FilteredTeams = [];

                                    for (var i = 0; i < results.length; i++) {
                                        for (var j = 0; j < results[i].Members.length; j++) {
                                            if (vm.get('task.OwnerID') == results[i].Members[j].Person.ID) {
                                                FilteredTeams.push(results[i]);
                                                break;
                                            }

                                        }
                                    }

                                    // If we're reloading the teams, the user may hav been changed. This means we have to check and make sure the selectedTeams are actually available to our new user. If not we splice them out.
                                    var selectedTeams = vm.get('selectedTeams');
                                    if (selectedTeams == null) {
                                        selectedTeams = [];
                                    }
                                    for (var i = 0; i < selectedTeams.length; i++) {
                                        for (var j = 0; j < FilteredTeams.length; j++) {
                                            if (selectedTeams[i].ID == FilteredTeams[j].ID) {
                                                break;
                                            }
                                            if (selectedTeams[i].ID != FilteredTeams[j].ID && j == FilteredTeams.length - 1) {
                                                selectedTeams.splice(i, 1);
                                                vm.set('teamsRemoved', true);
                                            }
                                        }
                                    }
                                    vm.set('selectedTeams', selectedTeams);
                                    options.success(FilteredTeams);
                                }).fail(options.error);
                            }
                            else {
                                secureAPI.GetAlignTeamsByPerson(masterPerson.ID).done(function (results) {
                                    var FilteredTeams = [];

                                    if (masterPerson.IsAdministrator) {
                                        FilteredTeams = results;
                                    }
                                    else {
                                        for (var i = 0; i < results.length; i++) {
                                            for (var j = 0; j < results[i].Members.length; j++) {
                                                if (masterPerson.ID == results[i].Members[j].Person.ID) {
                                                    FilteredTeams.push(results[i]);
                                                    break;
                                                }

                                            }
                                        }
                                    }

                                    // If we're reloading the teams, the user may hav been changed. This means we have to check and make sure the selectedTeams are actually available to our new user. If not we splice them out.
                                    var selectedTeams = vm.get('selectedTeams');
                                    if (selectedTeams == null) {
                                        selectedTeams = [];
                                    }
                                    for (var i = 0; i < selectedTeams.length; i++) {
                                        for (var j = 0; j < FilteredTeams.length; j++) {
                                            if (selectedTeams[i].ID == FilteredTeams[j].ID) {
                                                break;
                                            }
                                            if (selectedTeams[i].ID != FilteredTeams[j].ID && j == FilteredTeams.length - 1) {
                                                selectedTeams.splice(i, 1);
                                                vm.set('teamsRemoved', true);
                                            }
                                        }
                                    }
                                    vm.set('selectedTeams', selectedTeams);
                                    options.success(FilteredTeams);
                                }).fail(options.error);
                            }
                        }
                    },
                    schema: {
                        model: { id: "ID" }
                    },
                    batch: false,
                    sort: { field: "Name", dir: "asc" }
                }),
                dsSyncCategories: new kendo.data.DataSource({
                    transport: {
                        read: function (options) {

                            secureAPI.GetPersonSyncTaskCategoriesByCompanyID().done(function (syncTaskCategories) {
                                options.success(syncTaskCategories);
                                vm.set("loading", false);
                            });
                        }
                    },
                    schema: {
                        model: {
                            id: "ID"
                        }
                    },
                    sort: { field: "Name", dir: "asc" }
                }),
                dsHuddles: new kendo.data.DataSource({
                    transport: {
                        read: function (options) {
                            secureAPI.GetHuddlesByPerson(masterPerson.ID, masterCompany.ID, true).done(function (huddles) {                    
                                //J.S. we dont have an endpoint that returns a filtered list of huddles
                                //so we'll filter the huddles locally
                                //Also include a check to not filter based on an empty string
                                let filter = null;
                                if (options.data.filter != undefined
                                    && options.data.filter.filters != undefined
                                    && options.data.filter.filters.length != undefined
                                    && options.data.filter.filters.length > 0) {

                                    filter = options.data.filter.filters[0].value;
                                }

                                if (filter){
                                    let filteredHuddles = huddles.filter((huddle, index) => {
                                        return huddle.Name.toLowerCase().indexOf(filter) !== -1;
                                    });
                                    options.success(filteredHuddles);
                                }
                                else {
                                    options.success(huddles);
                                }
                            }).fail(options.fail);
                        }
                    },
                    schema: {
                        model: {
                            id: "ID"
                        }
                    },
                    sort: { field: "Name", dir: "asc" },
                    serverPaging: true,
                    serverFiltering: true,
                    serverSorting: true
                }),
                getRecurrenceInfo: function () {
                    var str = "This task will repeat "

                    var frequency = vm.get('task.Frequency');
                    var occurrences = vm.get('task.Recurrences');

                    if (occurrences == 0) {
                        if (frequency == 'daily') {
                            str += "daily forever.";
                        }
                        else if (frequency == 'weekly') {
                            str += "weekly forever.";
                        }
                        else if (frequency == 'monthly') {
                            str += "monthly forever.";
                        }
                        else if (frequency == 'monthly') {
                            str += "quarterly forever.";
                        }
                        else if (frequency == 'monthly') {
                            str += "yearly forever.";
                        }
                        else {
                            str += "forever.";
                        }
                    }
                    else {

                        if (frequency == 'daily') {
                            str += "daily for " + occurrences;
                            if (occurrences == 1) {
                                str += " day.";
                            }
                            else {
                                str += " days.";
                            }
                        }
                        else if (frequency == 'weekly') {
                            str += "weekly for " + occurrences;
                            if (occurrences == 1) {
                                str += " week.";
                            }
                            else {
                                str += " weeks.";
                            }
                        }
                        else if (frequency == 'monthly') {
                            str += "monthly for " + occurrences;
                            if (occurrences == 1) {
                                str += " month.";
                            }
                            else {
                                str += " months.";
                            }
                        }
                        else if (frequency == 'quarterly') {
                            str += "quarterly for " + occurrences;
                            if (occurrences == 1) {
                                str += " quarter.";
                            }
                            else {
                                str += " quarters.";
                            }
                        }
                        else if (frequency == 'yearly') {
                            str += "yearly for " + occurrences;
                            if (occurrences == 1) {
                                str += " year.";
                            }
                            else {
                                str += " years.";
                            }
                        }
                        else {
                            str += "forever.";
                        }
                    }
                    return str;
                },
                task: {},
                updateDrawerPostInvite: function (data) {
                    vm.set("showProgress", true);
                    debugger;
                    let users = secureAPI.SearchForPersons("").done(function (persons) {
                        for (let i = 0; i < persons.length; i++) {
                            if (persons[i].Email === data.userEmail) {
                                let person = persons[i];
                                vm.get("onSelectPerson")({ person: person });
                                break;
                            }
                        }
                        vm.set("showProgress", false);
                    }).fail(function (e) {
                        vm.set("showProgress", false);
                        $.radoloCommon.showError(e);
                    });
                },
                isOwnerSetForDataItem: function (dataID) {
                    let task = vm.get("task");
                    return task.ID >= 0;
                },
                toggleInviteInput: function(e){
                    var header = e.sender.header;
                    if (!this.currentPerson.IsAdministrator) {
                        header.hide();
                        return;
                    }

                    let inputText = e.filter.value;
                    vm.set("currentValue", inputText);

                    var headerText = $(e.sender.header[0].firstElementChild);

                    if (inputText == "") {
                        headerText.text("Start typing to invite a user");
                    } else {
                        headerText.text("+ Invite user \"" + inputText + "\" via email");
                        header.show();
                    }
                },
                clickInviteUser: function(e){
                    let currentValue = vm.get("currentValue");
                    let inputText = currentValue ? currentValue : "";
                    let currentTarget = vm.get("currentTarget");
                    let dataIDValue = vm.get("dataID");


                    //If a user was invited, the only info we'll have is their email
                    //so we can change the input field to display the email, but whatever uses this component
                    //will need to refresh their persons dataSource and find the invited user's ID
                    $.pubsub.subscribe(Align.Common.PubSubEvents.UsersInvited, (invitedUsers) => {

                        // Since invitedUsers is an array of Invitations[], get the first one
                        let invitedUser = null;
                        if (invitedUsers && Array.isArray(invitedUsers.Invitations) && invitedUsers.Invitations.length > 0) {
                            invitedUser = invitedUsers.Invitations[invitedUsers.Invitations.length - 1]; // Get the first invited user
                        }

                        let userEmail = null;
                        if (invitedUser) {
                            userEmail = invitedUser.EmailAddress;
                            currentTarget.value = invitedUser.EmailAddress;
                            //The above line is only for OnePagePlan.ts and UpdateCriticalNumberValues.ts
                            //other areas, such as EditHuddleGroup.ts, have an easier time in updating the input field
                            let dataID = -1;
                            if (currentTarget.kendoBindingTarget) {
                                dataID = currentTarget.kendoBindingTarget.source.ID;
                            }
                            else {
                                dataID = dataIDValue;
                            }
                            //This method needs to be defined outside of the component, because the way we'll fetch persons 
                            //and update data is different from e.g. updateCriticalNumberValue drawer and editPriority drawer
                            vm.updateDrawerPostInvite({ dataID: dataID, userEmail: userEmail });
                        }
                    });

                    $.pubsub.publish("inviteUserFromDropdown", inputText);
                },

                cleanupChangeLogs: function () {
                    // Clean up when dialog is closed or new task is loaded
                    vm.set("IsTaskLogsVisible", false);
                    vm.set("logCount", 0);
                    vm.set("changeLogs", []);

                    // Reset UI state
                    const logContainer = document.getElementById("logContainer");
                    const toggleArrow = document.getElementById("toggleArrow");

                    if (logContainer) {
                        logContainer.style.display = "none";
                    }
                    if (toggleArrow) {
                        toggleArrow.className = "k-icon k-i-arrow-60-down";
                        toggleArrow.title = "Show Logs"; // Reset tooltip to initial state
                    }
                },
            });

            //vm = new Align.Component.InviteUserDropdownComponent(vm, $content);

            $.pubsub.subscribeSingle("UserAddedFromActionButton", function (refreshDatasource) {
                vm.get("dsSearchPersons").read();
            });

            //kendo.bind($content, vm);
            $.radoloCommon.bindKendoComponents($content, vm);

            //updated by vivek - start - Task Change Logs rendering and toggle functions
            function renderTaskLogs(logs) {
                const $container = $("#logListContainer");
                $container.empty();

                // Add CSS styling if not already added
                if (!$("#taskLogFormatStyles").length) {
                    $("<style id='taskLogFormatStyles'>")
                        .prop("type", "text/css")
                        .html(`
                .change-details-col { width: 50%; }
                .table-bordered td { padding: 8px; vertical-align: top; }
                .change-details-col ul { margin: 0; padding-left: 20px; }
                .change-details-col li { margin-bottom: 3px; }
                .user-cell {
                    display: flex;
                    align-items: center;
                }
                .user-avatar {
                    width: 24px;
                    height: 24px;
                    border-radius: 50%;
                    margin-right: 8px;
                }
            `)
                        .appendTo("head");
                }

                if (!logs || logs.length === 0) {
                    $container.append("<tr><td colspan='3' style='text-align: center; color: #666; font-style: italic; padding: 20px;'>No changes have been logged for this task yet.</td></tr>");
                    return;
                }

                logs.forEach(function (log) {
                    const rowHtml = `
            <tr>
                <td>${kendo.toString(kendo.parseDate(log.ActionDate), "G")}</td>
                <td>
                    <div class="user-cell">
                        <img src="${log.ProfilePicture}" title="${log.UserName}" alt="Profile" class="user-avatar">
                        <span>${log.UserName}</span>
                    </div>
                </td>
                <td class="change-details-col">${log.ChangeDetails}</td>
            </tr>
        `;
                    $container.append(rowHtml);
                });
            }

            // Toggle functionality for change logs (following EditCompanyMetricDialog pattern)
            // Remove the old event handler and replace with this
            $(document).off('click', '#toggleLogBtn').on('click', '#toggleLogBtn', function () {
                console.log("[TaskChangeLogs] Toggle button clicked");

                const logContainer = document.getElementById("logContainer");
                const toggleArrow = document.getElementById("toggleArrow");

                if (!logContainer || !toggleArrow) {
                    console.error("[TaskChangeLogs] Toggle elements not found");
                    return;
                }

                const isVisible = logContainer.style.display === "block";
                console.log("[TaskChangeLogs] Current visibility:", isVisible);

                // Toggle visibility
                logContainer.style.display = isVisible ? "none" : "block";

                // Update arrow
                // Update arrow and tooltip
                if (isVisible) {
                    // Logs are being hidden
                    toggleArrow.className = "k-icon k-i-arrow-60-down";
                    toggleArrow.title = "Show Logs";
                } else {
                    // Logs are being shown
                    toggleArrow.className = "k-icon k-i-arrow-60-up";
                    toggleArrow.title = "Hide Logs";
                }

                console.log("[TaskChangeLogs] New visibility:", logContainer.style.display);
            });

            //updated by vivek - end - Task Change Logs rendering and toggle functions
            
            $.pubsub.subscribeSingle("TaskReady", function (task) {
                // Clean up previous state first
                vm.cleanupChangeLogs();
                //2022-02-09 RJK: Moved Feature Flag evaluation here to provide a better chance that the Company information is loaded
                //and the Feature Flag information is up-to-date.
                vm.set('bIsTeamsFeatureEnabled', featureFlagEvaluator.isFeatureEnabled('Teams.Web'));
                vm.set('bIsOKRFeatureEnabled', featureFlagEvaluator.isFeatureEnabled('Methodologies.OKR.Web'));
                vm.set('bIsHuddlesFeatureEnabled', featureFlagEvaluator.isFeatureEnabled('Huddles.Web'));
                //update by vivek on 04-09-2025
                vm.set('originalEndDate', task.EndDate);
                vm.set('bIsHuddlesAssociatedTasksEnabled', featureFlagEvaluator.isFeatureEnabled('Huddles.AssociatedTasks.Web'));
                vm.set('bIsPeriodTransitionEnabled', featureFlagEvaluator.isFeatureEnabled('PeriodTransition.Web'));
                // VSS 07th Nov 2025 - Document Management feature flag
                vm.set('isDocumentManagementFeatureEnabled', featureFlagEvaluator.isFeatureEnabled('Document Management.Web'));

                //VSS
                // Auto-align first priority for Precon Playbook brand on new tasks
                if (masterCompany.AlignBrandID === 6 && task.ID < 1 && task.AutoSelectFirstPriority) {
                    // Auto-select the first priority from the dropdown
                    vm.get("dsPriorities").read().done(function () {
                        var priorities = vm.get("dsPriorities").data();
                        if (priorities && priorities.length > 0) {

                            // Sort priorities to get the latest one (by ID descending - higher ID = more recent)
                            var sortedPriorities = priorities.slice().sort(function (a, b) {
                                return (b.ID || 0) - (a.ID || 0);
                            });

                            var firstPriority = sortedPriorities[0];
                            vm.set("rock", firstPriority);

                            // Trigger onChangeRock to apply visibility settings from the selected priority
                            // This ensures task inherits security settings from the project/phase
                            // Create a mock event object since onChangeRock expects it
                            var mockEvent = {
                                sender: {
                                    element: [document.getElementById("project-phase")]
                                }
                            };
                            vm.onChangeRock(mockEvent);

                            // Make the project-phase input readonly after auto-selection (Precon brand)
                            vm.onSelectProjectPhase();
                        }
                    });
                }

                if (task.ApprovedUsers == null) {
                    task.ApprovedUsers = [];
                }
                if (task.RockApprovedUsers) {
                    task.ApprovedUsers = task.RockApprovedUsers;
                }
                else {
                    if (task.RockApprovedUsers == null || task.RockApprovedUsers == undefined) {
                        task.RockApprovedUsers = [];
                    }
                }

                if (task.Teams == null) {
                    task.Teams = [];
                }

                if (task.RockTeams == null || task.RockTeams == undefined) {
                    task.RockTeams = [];
                }

                //2023-06-30 RJK: Is the Huddle Type needed here?
                if (task.HuddleID != null && task.HuddleID > 0) {
                    var huddle = { ID: task.HuddleID, Name: task.Huddle, Tags: task.HuddleTags || [] };
                    vm.set('selectedHuddle', huddle);
                }

                vm.set("task", task);
                taskBeforeEdit = Align.Common.deepCopy(task);

                if (task.ID < 1) {
                    vm.set("IsNewTask", true);
                    vm.onEditTaskEntry();

                    vm.dsSyncCategories.read().done(function () {

                        var data = vm.dsSyncCategories.data();
                        
                        if (data.length < 1) {
                            if (vm.get("taskProviderID") > 0 && vm.dsSyncCategories.total() < 1) {
                                $.radoloDialog.showAsWindow({
                                    title: "Whoops",
                                    message: "You don't have any sync categories set up. Add at least one sync category. <a href='/Application/ManageIntegrations.aspx'>Manage Task Integration</a>",
                                    buttons: [{ title: "OK" }]
                                });
                            }
                        } else {
                            //set the default
                            $.each(data, function (index, item) { 
                                if (item.IsDefault) {
                                    vm.set("SyncCategory", item);
                                }
                            });
                        }

                    });
                } else {
                    vm.set("IsNewTask", false);
                    vm.dsSyncCategories.read().done(function () {

                        var data = vm.dsSyncCategories.data();
                        $.each(data, function (index, item) {
                            if (item.Name === task.SyncCategory) {
                                vm.set("SyncCategory", item);
                            }
                        });
                    });

                }
                vm.setVisibilityFromPriority(task);

                //ensure the "Give Users Access" field is populated on load
                if (task.Visibility && task.Visibility === "Users") {
                    vm.get("dsCompanyPersonsAvailableForSecureTasks").read();
                }

                //If a user is not a member of the huddle, they shouldn't be allowed to change the huddle the task is related to 11/22/23 JP
                
                vm.set("isNotAMemberOfHuddle", task.IsHuddleMember);

                if (vm.get("selectedHuddle")) {
                    if (!vm.get("isNotAMemberOfHuddle")) {
                        $('#huddle-related-readonly').kendoTextBox({
                            value: kendo.toString(vm.get('selectedHuddle.Name')),
                        })
                    }
                } else {
                    //then it should be assignable to a huddle
                    vm.set("isNotAMemberOfHuddle", true);
                }     

                //updated by satwick - start - Load task change logs
                console.log("[TaskChangeLogs] Task loaded - ID:", task.ID);

                // Add a small delay to ensure DOM is ready
                setTimeout(function () {
                    vm.loadChangeLogs();
                }, 100);
                //updated by satwick - end - Load task change logs

                //VSS 29th Oct 2025 - start - Load task documents and setup event handlers
                if (task.ID && task.ID > 0) {
                    vm.get('dsTaskDocuments').read();
                }

                if (!masterPerson.IsViewer) {
                    $('#addTaskDocumentBtn').show();
                }

                $('body').kendoTooltip({
                    filter: '#editTaskDialogContent .task-document-name',
                    position: 'top',
                    autoHide: true,
                    content: function(e) {
                        var $target = $(e.target);
                        var description = $target.attr('data-tooltip-description');
                        var date = $target.attr('data-tooltip-date');
                        var owner = $target.attr('data-tooltip-owner');

                        var plainDescription = '';
                        if (description) {
                            var tempDiv = document.createElement('div');
                            tempDiv.innerHTML = description;
                            plainDescription = tempDiv.textContent || tempDiv.innerText || '';
                        }

                        // Build tooltip content as HTML with left alignment
                        var tooltipContent = '<div style="text-align: left;">';
                        if (plainDescription) {
                            tooltipContent += '<strong>Description:</strong> ' + plainDescription + '<br />';
                        }
                        tooltipContent += '<strong>Uploaded Date:</strong> ' + date + '<br />';
                        tooltipContent += '<strong>Owner:</strong> ' + owner;
                        tooltipContent += '</div>';

                        return tooltipContent;
                    }
                });
                //VSS 29th Oct 2025 - end - Load task documents and setup event handlers
            });
            $.pubsub.publish("NeedTask");

             // clicking on the input field should open up the date picker. 
            $("#inputdatepicker").kendoDatePicker();
            var inputdatepicker = $("#inputdatepicker").data("kendoDatePicker");
            $('#inputdatepicker').click(function () {
              
                inputdatepicker.open();
            });

            //J.S. if the vm object extends the InviteUserDropdown component the assignedUsers list
            //begins to have unexpected behaviors, such as names displaying twice
            //as a result the functions defined there exist here, so any changes here must be
            //applied there as well
            $(".inputFieldClass").on("focus", (event) => {
                //we need a reference to this current element, because when we click on the invite user option
                //it will be difficult to find a reference to this specific element again
                vm.set("currentTarget", event.target);

                //J.S only CNs and QAs care about dataID, because we need the ID of the object so we can assign its owner
                if (event.target && event.target.kendoBindingTarget) {
                    vm.set("dataID", event.target.kendoBindingTarget.source.ID);
                }
                if (vm.get("isOwnerSetForDataItem")(vm.get("dataID")) === false) {
                    var autoComplete = $(event.target).data("kendoAutoComplete");
                    autoComplete.search("");
                }
            });

            $(".inviteDropdownClass").on("click", (event) => {
                vm.clickInviteUser(event);
            });

        }); 
    </script>
    <style>
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

.form .label {
  margin-right: 5px;
}

.k-icon {
  font-size: 12px;
  margin-right: 10px;
  color: black;
}

.log-line {
  flex: 1;
  height: 1px;
  background-color: black;
}
    </style>
</asp:Content>
