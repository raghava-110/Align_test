<%@ Page Title="" Language="C#" MasterPageFile="~/MasterApplication.master" %>
<%@ OutputCache CacheProfile="Page"%>


<asp:Content ID="Content1" ContentPlaceHolderID="MasterApplicationContent" runat="server">

    <div id="content" data-bind='visibility: loaded, showProgress: showProgress' style="visibility: hidden;">
        <!-- Sticky Header -->
        <div class="content-header">
            <div class="content-title">Manage Tasks <span data-role="helpbutton" data-helpname="Tasks"></span></div>
            <%--2023-10-31 ND: Removed Navigation Headers--%>
           <%-- <div class="content-title flex" data-align-feature-flag="SimplifiedNavigation.Web" data-align-feature-flag-behavior="visible">
                <input class="header-dropdown setMaxWidth expand-to-fit" 
                        id="navigation-header-dropdown"
                        data-role="dropdownlist" 
                        data-text-field="name" 
                        data-value-field="path" 
                        data-auto-bind="false"
                        data-bind="value: headerNavigation.selected, source: headerNavigation.headers, events: { change: headerNavigation.select }" />
                <span data-role="helpbutton" data-helpname="Tasks"></span>
            </div>--%>
            <div class="subnav" data-bind="invisible: showEmptyState">
                <button class="button green" data-bind="click: clickNewTask, invisible: currentPerson.IsViewer">
                    <span class="icon ico-plus-circle"></span>
                    New Task
                </button>
                <button class="button green ghost" data-bind="click: clickPrintTasks">
                    <span class="icon ico-printer"></span>
                    Print Tasks
                </button>
            </div>
        </div>

        <div class="margin-top-more">
            <div class="module padded-wrapper no-margin-bottom">
                <div class="module-inner">
                    <div class="form-normal filter-forms">
                        <label>
                            <div class="label">
                                Search Task Owners
                            </div>
                            <input class="input autocomplete-off"
                                data-role="autocomplete"
                                data-auto-bind="false"
                                data-filter="contains"
                                data-template="personTemplate"
                                placeholder="User Name"
                                data-text-field="Name"
                                data-bind="value: SearchPerson, source: dsSearchPersons, events: { change: showTasksForPerson }"
                                spellcheck="false"/>
                        </label>
                        <label>
                            <div class="label">
                                Search Tasks
                            </div>
                            <input class="input autocomplete-off"
                                placeholder="Task Name"
                                data-bind="events: { keyup: onKeyupFilterTasks }" />
                        </label>
                    </div>
                    <div class="margin-top form-normal filter-forms">
                        <label data-bind="visible: showTeamsFilter">
                            <div class="label">
                                Search Task Owners by Team
                            </div>
                            <select
                                id="multiTeamFilter"
                                data-role="multiselect"
                                data-auto-bind="false"
                                data-placeholder="Team Name"
                                multiple="multiple"
                                data-filter="contains"
                                data-text-field="Name"
                                data-bind="value: selectedTeam, source: dsUserTeams, events: { change: showTasksForTeams }">
                            </select>
                        </label>
                        <label class="checker-wrapper padding-top-2" data-bind="invisible:bIsWhoWhatWhenFeatureEnabled">
                            <input title="Show Completed Tasks" type='checkbox' data-role="uniform" data-bind='value: ShowCompleted, events: { change: changeShowCompleted }' data-change-click-only="true" />
                            <span class="label">
                                Show Completed Tasks
                            </span>
                        </label>
                        <label class="center-self-vertically" data-bind="visible:bIsWhoWhatWhenFeatureEnabled">
                            <input id="switch" title="Show Completed Tasks" data-role="switch" data-bind='checked: ShowCompleted, events: { change: changeShowCompleted }'/>
                            <span class="label">Show Completed</span>
                        </label>
                    </div>
                    <div class="margin-top form-normal filter-forms">
                        <div style="width: 50%; margin-right: 2em;">
                            <label data-bind="visible: bIsOKRFeatureEnabled">
                                <div class="label">
                                    Search By Assigned To
                                </div>
                                <select
                                    id="multiAssignedToFilterOKR"
                                    data-role="multiselect"
                                    data-auto-bind="false"
                                    data-placeholder="Click to search OKRs and Huddles"
                                    multiple="multiple"
                                    data-filter="contains"
                                    data-value-field="ID"
                                    data-text-field="Name"
                                    data-item-template="assignedToItemTemplate"
                                    data-bind="value: selectedAssignedTo, source: dsAssignedToItems, events: { change: showTasksForAssignedTo }">
                                </select>
                                <script id="assignedToItemTemplate" type="text/x-kendo-template">
                                    <span>#=Name#</span>
                                    # if (Type === 'Rock') { #
                                        <span class="icon ico-priorities-feather margin-left-less"></span>
                                    # } else if (Type === 'Huddle') { #
                                        <span class="icon ico-weekly-huddle-feather margin-left-less"></span>
                                    # } #
                                </script>
                            </label>
                            <label data-bind="visible: isPreconBrandWithoutOKR">
                                <div class="label">
                                    Search By Assigned To
                                </div>
                                <select
                                    id="multiAssignedToFilterPrecon"
                                    data-role="multiselect"
                                    data-auto-bind="false"
                                    data-placeholder="Click to search Project or Phase and Huddles"
                                    multiple="multiple"
                                    data-filter="contains"
                                    data-value-field="ID"
                                    data-text-field="Name"
                                    data-item-template="assignedToItemTemplatePrecon"
                                    data-bind="value: selectedAssignedTo, source: dsAssignedToItems, events: { change: showTasksForAssignedTo }">
                                </select>
                                <script id="assignedToItemTemplatePrecon" type="text/x-kendo-template">
                                    <span>#=Name#</span>
                                    # if (Type === 'Rock') { #
                                        <span class="icon ico-priorities-feather margin-left-less"></span>
                                    # } else if (Type === 'Huddle') { #
                                        <span class="icon ico-weekly-huddle-feather margin-left-less"></span>
                                    # } #
                                </script>
                            </label>
                            <label data-bind="visible: isDefaultBrand">
                                <div class="label">
                                    Search By Assigned To
                                </div>
                                <select
                                    id="multiAssignedToFilterDefault"
                                    data-role="multiselect"
                                    data-auto-bind="false"
                                    data-placeholder="Click to search Priorities and Huddles"
                                    multiple="multiple"
                                    data-filter="contains"
                                    data-value-field="ID"
                                    data-text-field="Name"
                                    data-item-template="assignedToItemTemplateDefault"
                                    data-bind="value: selectedAssignedTo, source: dsAssignedToItems, events: { change: showTasksForAssignedTo }">
                                </select>
                                <script id="assignedToItemTemplateDefault" type="text/x-kendo-template">
                                    <span>#=Name#</span>
                                    # if (Type === 'Rock') { #
                                        <span class="icon ico-priorities-feather margin-left-less"></span>
                                    # } else if (Type === 'Huddle') { #
                                        <span class="icon ico-weekly-huddle-feather margin-left-less"></span>
                                    # } #
                                </script>
                            </label>
                        </div>
                        <label data-bind="visible: isPreconBrand">
                            <div class="label">
                                Search By Projects
                            </div>
                            <select
                                id="multiProjectFilter"
                                data-role="multiselect"
                                data-auto-bind="false"
                                data-placeholder="Click to search Projects"
                                multiple="multiple"
                                data-filter="contains"
                                data-value-field="ProjectPriorityID"
                                data-text-field="Name"
                                data-bind="value: selectedProjects, source: dsCompanyProjects, events: { change: showTasksForProjects }">
                            </select>
                        </label>

                </div>
                    </div>
            </div>
            <div class="empty-state padding-more" data-bind="visible: showEmptyState">
                <div class="empty-state-inner">
                    <div class="empty-state-img" style="background-image: url('../img/task_sheet.svg');"></div>
                    <h3>No Tasks to Display</h3>
                    <h4 class="padding-bottom">Break down Priorities into achievable, discrete tasks.</h4>
                    <div>
                        <button class="button small btn-filled" data-bind="click: clickNewTask, invisible: currentPerson.IsViewer">
                            <span class="icon ico-plus-circle"></span>ADD NEW TASK
                        </button>
                    </div>
                </div>
            </div>

            <div class="module padded-wrapper" data-bind="invisible: showEmptyState">
                <div class="module-inner">
                    <h3>
                        Tasks
                    </h3>
                    <%--2023-06-21 ND: Use TasksTableComponent.  Template has been moved to TasksTable.template.html--%> 
                    <%--This section is invisible until the WWW feature is available--%>
                    <div data-bind="visible:bIsWhoWhatWhenFeatureEnabled">
                        <div data-component="TasksTableComponent"></div>
                    </div>

                    <div data-bind="invisible:bIsWhoWhatWhenFeatureEnabled">
                        <div class="empty-state padding-more" data-bind="visible: showEmptyState">
                            <div class="empty-state-inner">
                                <div class="empty-state-img" style="background-image: url('../img/task_sheet.svg');"></div>
                                <h3>No Tasks to Display</h3>
                                <h4 class="padding-bottom">Break down Priorities into achievable, discrete tasks.</h4>
                                <div>
                                    <button class="button small btn-filled" data-bind="click: clickNewTask, invisible: currrentPerson.IsViewer">
                                        <span class="icon ico-plus-circle"></span>ADD NEW TASK</button>
                                </div>
                            </div>
                         </div>

                        <div data-bind="invisible: showEmptyState">
                            <div>                             
                                <div class="empty-state"
                                        data-bind="visible: showFilteredEmptyState">
                                    <div class="empty-state-inner no-margin">
                                        <h3>No Tasks to Display</h3>
                                        <h4>Try changing your search criteria or creating a new task.</h4>
                                    </div>
                                </div>
                                <!--2020-02-04 RJK: alwaysVisible isn't set because User Preference sets page size and we want to preserve the User's ability to change it.-->
                                <div data-bind="visible: bIsOKRFeatureEnabled">
                                    <div class="grid no-scroll"
                                        data-role="grid"
                                        data-bind="source: dsTasks, invisible: showFilteredEmptyState"
                                        data-sortable='true'
                                        data-scrollable='false'
                                        data-auto-bind='false'
                                        data-pageable='{pageSizes: [5,10,20,30,50],buttonCount:4}'
                                        data-row-template='tasks-row-template'
                                        data-columns='[{title: " ", width: 5},{title: " ", width: 5},{ title: "Task", field:"ShortDescription", width: 300},{title:"Due Date", field:"EndDate", width: 40},{title:"Created By", field:"CreatedBy", width: 40},{title:"Assigned To", field:"Owner", width: 40},{title:"Associated OKR", field:"RockName", width: 40},{title: " ", width: 5}, {title: " ", width: 5}, {title: " ", width: 5}]'>
                                    </div>
                                </div>
                                <div data-bind="invisible: bIsOKRFeatureEnabled">
                                    <div class="grid no-scroll"
                                        data-role="grid"
                                        data-bind="source: dsTasks, invisible: showFilteredEmptyState"
                                        data-sortable='true'
                                        data-scrollable='false'
                                        data-auto-bind='false'
                                        data-pageable='{pageSizes: [5,10,20,30,50],buttonCount:4}'
                                        data-row-template='tasks-row-template'
                                        data-columns='[{title: " ", width: 5},{title: " ", width: 5},{ title: "Task", field:"ShortDescription", width: 300},{title:"Due Date", field:"EndDate", width: 40},{title:"Created By", field:"CreatedBy", width: 40},{title:"Assigned To", field:"Owner", width: 40},{title:"Associated Priority", field:"RockName", width: 40},{title: " ", width: 5}, {title: " ", width: 5}, {title: " ", width: 5}]'>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            
        </div>
    </div>
    <!--#include file="/Templates/templateTaskRowTemplates.template.html"-->
    <!--#include file="/Templates/templateCompanyPersonsDropdown.template.html"-->
    <script src="/js/Generated/ManageTasks.js" type="text/javascript"></script>
</asp:Content>
