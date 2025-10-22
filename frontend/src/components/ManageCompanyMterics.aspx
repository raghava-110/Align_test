<%@ Page Title="" Language="C#" MasterPageFile="~/MasterApplication.master" %>

<%@ OutputCache CacheProfile="Page" %>


<asp:Content ID="Content1" ContentPlaceHolderID="MasterApplicationContent" runat="server">
    <div id="content" class="full-page" data-bind="visibility: loaded, showProgress: showProgress" style="visibility: hidden;">
        <!-- Sticky Header -->
        <div class="content-header">
            <div class="content-title">Manage Metrics <span class="radoloHelpButton" data-role="helpbutton" data-helpname="CompanyMetrics"></span>
            </div>
            <div class="subnav" data-bind="invisible: showProgress, invisible: hideNavBar">
                <button class="button btn-filled" data-bind="click: clickAddCompanyMetric, invisible: currentPerson.IsViewer">
                    <span class="icon ico-plus-circle"></span>Add Metric
                </button>
                <label class="button btn-ghost" data-bind="invisible: showEmptyState">
                    <input type='checkbox' data-role="uniform" data-bind='value: IsFilteringCompanyMetrics' data-change-click-only="true" />
                    Filter Metrics
                </label>
                <!-- Single Bulk Create Button -->
                <button class="button btn-filled" data-bind="click: clickBulkCreate, visible: showExcelIntegration">
                    <span class="icon ico-upload"></span>Bulk Create
                </button>
            </div>

        </div>

        <div class="margin-top">
            <div class="filter-wrapper" data-bind="class: { filter: IsFilteringCompanyMetrics }">
                <div class="module">
                    <div class="module-inner">
                        <div class="form full">
                            <ul class="form-normal half-inputs no-margin">
                                <li>
                                    <label>
                                        <div class="label">
                                            Filter People
                                        </div>
                                        <select
                                            id="multiUserFilter"
                                            data-role="multiselect"
                                            data-item-template="personTemplate"
                                            data-auto-bind="false"
                                            data-placeholder="Person's Name"
                                            multiple="multiple"
                                            data-filter="contains"
                                            data-text-field="Name"
                                            data-bind="value: selectedPerson, source: dsCompanyPersons, events: { change: filterCompanyMetrics }"
                                            spellcheck="false">
                                        </select>
                                    </label>
                                </li>                           
                                <li>
                                    <label>
                                        <div class="label">
                                            Filter Metrics
                                        </div>
                                        <input class="input" 
                                            type="text" 
                                            placeholder="Metric Name" 
                                            data-bind="value: filterCompanyMetricsKeyword, events: { change: filterCompanyMetricsByKeyword }">
                                    </label>
                                </li>
                            </ul>
                            <ul class="form-normal half-inputs no-margin">
                                <li>
                                    <label>
                                        <div class="label">
                                        Filter Status
                                        </div>
                                        <input class="dropdown" 
                                            id="metricStatusFilter" 
                                            data-role="dropdownlist" 
                                            data-bind="value: selectedStatus, source: getAllStatusValues, events: { change: filterCompanyMetrics }" 
                                            data-option-label="All Statuses"
                                            placeholder="All Statuses"
                                            />
                                    </label>    
                                </li>
                            </ul>

                            <div class="flex">
                                <!--Drop-down check list for Status values? -->
                                <%--<label class="check-textbox margin-right">
                                    <input title="Show Only Company Priorities" type='checkbox' data-role="uniform" data-bind='value: showOnlyCompanyPriorities, events: { change: filterPriorities }' data-change-click-only="true" />
                                    <span class="check-label">Company Priority
                                    </span>
                                </label>--%>
                                <button class="btn-text align-items-unset" data-bind="click: clickClearFilteredSearch">
                                    <span class="icon ion-close"></span>
                                    <span class="margin-left-less">Clear Filter
                                    </span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="module" data-bind="invisible: showEmptyState">
                <div class="module-inner manage-metrics">
                    <div class="metrics-header padding-bottom">
                        <h3 class="met-owner"></h3>
                        <h3 class="met-name">Metric</h3>
                        <h3 class="met-val">Current Value</h3>
                        <h3 class="met-last-updt">Last Updated</h3>
                        <h3 class="met-conns">Connections</h3>
                        <h3 class="met-buttons"></h3>
                    </div>

                    <div class="horizontal-divider--light"></div>

                    <div class="inline-view-metrics"
                        data-role="listview"
                        data-auto-bind="false"
                        data-scrollable="false"
                        data-template='company-metric-row-template'
                        data-bind="source: dsCompanyMetrics, invisible: showFilteredEmptyState">
                    </div>
                </div>
            </div>
            <div class="empty-state">
                <div class="empty-state-inner" data-bind="visible: showEmptyState">
                    <div class="empty-state-img" style="background-image: url('../img/rocket_launch.svg')"></div>
                    <h3>Track your important Metrics</h3>
                    <!--2023-06-23 RJK: There was a link to open an Appcue but it was the Picking Priorities Appcue. Switched to link to a page in the Align Academy  -->
                    <h4>Don't know where to start? Read here to
                        <a class="teal-link" href="https://application.aligntoday.com/Application/Help.aspx?ID=281" target="_blank"> learn about Metrics</a>
                        .
                    </h4>
                    <div>
                        <div class =" margin-top-less">
                            <button class="button small btn-filled" data-bind="click: clickAddCompanyMetric, invisible: currentPerson.IsViewer">
                                <span class="icon ico-plus-circle"></span>Add Metric
                            </button>
                        </div>
                    </div>
                </div>
                <div class="empty-state-inner" data-bind="visible: showFilteredEmptyState">
                    <h3>No Metrics found.</h3>
                    <h4>Try changing your filtered keyword or Owner.</h4>
                </div>
            </div>
        </div>
    </div>

    <!--#include file="/Templates/templateCompanyMetricRow.template.html"-->
    <!--#include file="/Templates/templateCompanyPersonsDropdown.template.html"-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/exceljs/4.4.0/exceljs.min.js"></script>
    <script src="/js/Generated/ManageCompanyMetrics.js" type="text/javascript"></script>
</asp:Content>


