<%@ Page Title="" Language="C#" MasterPageFile="~/MasterApplication.master" %>

<%@ OutputCache CacheProfile="Page" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MasterApplicationContent" runat="server">
    <div id="content" data-bind='visibility: loaded' style="visibility: hidden;">
        <!-- Sticky Header -->
        <div class="content-header">
            <span class="content-title">Dashboard</span>
            <%--2023-10-31 ND: Removed Navigation Header--%>
            <%--<span data-align-feature-flag="SimplifiedNavigation.Web" data-align-feature-flag-behavior="invisible" class="content-title">Dashboard</span>
            <div class="content-title flex" data-align-feature-flag="SimplifiedNavigation.Web" data-align-feature-flag-behavior="visible">
                <input class="header-dropdown setMaxWidth expand-to-fit" 
                        id="navigation-header-dropdown"
                        data-role="dropdownlist" 
                        data-text-field="name" 
                        data-value-field="path" 
                        data-auto-bind="false"
                        data-bind="value: headerNavigation.selected, source: headerNavigation.headers, events: { change: headerNavigation.select }" />
            </div>--%>
            <button class="button btn-ghost--s margin-left-less" data-bind="click: clickEditDashboard">
                <span class="icon ico-edit-"></span>Edit
            </button>
            <span class="radoloHelpButton margin-left-less" data-role="helpbutton" data-helpname="DashboardEdit"></span>
            <!--Date Controller-->
            <div class="date-controller flex center-self-vertically margin-left-double">
                <span data-bind="visible: periodIsLoading">Retrieving period...</span>
                <div class="date-range dash-cn"  data-bind="invisible: periodIsLoading">
                    <button class="link criticalnumberperiod ion-chevron-left dash-cn" data-bind="visible: previousPeriodVisible, click: clickGetPreviousPeriod" title="Go to previous period" style="display: none;"></button>
                    <div data-format="d" data-bind="text: period.StartDate"></div>
                    <div class="progress-container margin-top-neg-one">
                        <div class="padding-right-less">
                        </div>
                        <div class="periodGauge desktop-only" data-bind="attr: {title: periodProgressToolTipText">
                            <div class="periodTimeElapsed" data-bind="style: {width: periodTimeElapsedPercentage}">
                            </div>
                        </div>
                    </div>
                    <button class="link larger-font dark icon ion-compose margin-left-less margin-right-none padding-none margin-top-neg-three" data-bind='click: clickEditCurrentPeriod, visible: allowEditPeriod' title="Edit the period" style="display: none;">

                    </button>
                    <div class="margin-left-less" data-format="d" data-bind="text: period.EndDate"></div>
                    <span class="current-period dash-cn" data-bind="visible: isCurrentPeriod"> (Current)</span>
                    <button class="link criticalnumberperiod ion-chevron-right dash-cn" data-bind="visible: nextPeriodVisible, events: { click: clickGetNextPeriod }" title="Go to next period" style="display: none;"></button>
                    <button class="link larger-font dark icon ico-plus-circle margin-top-neg-five" data-bind=" visible: canCreateNewPeriod, events: { click: clickCreateNewPeriod }" title="Create a new period" style="display: none;">
                    </button>
                </div>
            </div>
            <!--<div class="subnav">

            </div>-->
        </div>
        <div data-bind="invisible: showEmptyState">
            <div data-role='dashboard' class="dashboard" data-selected-read-url="/Services/SecureAPI.svc/GetDashboardComponentsForPerson"
                data-selected-create-url="/Services/SecureAPI.svc/SaveDashboardComponentForPerson"
                data-selected-update-url="/Services/SecureAPI.svc/SaveDashboardComponentForPerson"
                data-selected-destroy-url="/Services/SecureAPI.svc/DeleteDashboardComponentForPerson"
                data-available-read-url="/Services/SecureAPI.svc/GetDashboardComponentTypesForPerson"
                data-auto-bind="false" data-bind='source: dsDashboard, events: { dataBound: dashboardBound, componentPreferencesChanged: componentPreferencesChanged, componentMessageShared: componentMessageShared }'
                data-dashboard-type="My">
            </div>
        </div>

        <div class="empty-state margin-top-more-and-a-half">
            <div class="empty-state-inner" data-bind="visible: showEmptyState" style="display: none;">
                <div class="empty-state-img" style="background-image: url('../img/rocket_launch.svg')"></div>
                <div data-bind="invisible: showProgress">
                    <h3>Your Dashboard is Empty. </h3>
                    <h4>You can add components to your Dashboard to track your tasks, goals, and metrics throughout the organization.</h4>
                    <div class="margin-top-less">
                        <button class="button small btn-filled" data-bind="click: clickEditDashboard">
                            <span class="icon ico-plus-circle margin-right-less"></span>
                            Add a Dashboard Component
                        </button>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <!--#include file="/Templates/templateTaskRowTemplates.template.html"-->
    <!--#include file="/Templates/templateDashboardCriticalNumber.template.html"-->
    <!--#include file="/Templates/KPICards.template.html"-->
    <script src="/js/plugInDashboard.js" type="text/javascript"></script>
    <script src="/js/Generated/Dashboard.js" type="text/javascript"></script>
</asp:Content>
