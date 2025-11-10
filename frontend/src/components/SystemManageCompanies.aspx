<%@ Page Title="" Language="C#" MasterPageFile="~/MasterApplication.master" %>

<%@ OutputCache CacheProfile="Page"%>

<asp:Content ID="Content1" ContentPlaceHolderID="MasterApplicationContent" runat="server">
    <div id="content" data-bind='visibility: loaded' style="visibility: hidden;">
        <!-- Sticky Header -->
        <div class="content-header">
            <div class="content-title">Manage Companies</div>
            <div class="subnav" data-bind="invisible: showProgress, invisible: hideNavBar">
                <button id="SystemManageCompanies_CreateCompanyButton" class="button btn-filled" data-bind="click: clickCreateCompany">
                    <span class="icon ico-plus-circle"></span>Create Company
                </button>
                <a class="button btn-ghost--m" href="/Application/SystemAdministratorBillingHistory.aspx">
                    <span class="icon ion-cash"></span>
                    Billing History
                </a>
                <a class="button btn-ghost--m" href="/Application/SystemAdministratorBookedRevenue.aspx">
                    <span class="icon ion-briefcase"></span>
                    Booked Revenue
                </a>
            </div>
        </div>

        <div class="margin-top-more">
            <ul class="form-normal half-inputs">
                <li>
                    <label>
                        <div class="label">Search Company</div>
                        <input class="input" 
                            type="text" 
                            data-bind='value: filter, events: { keyup: filterResultsAtPageOne }'
                            placeholder="Company Name..." 
                            data-role='inputplaceholder' />
                    </label>
                </li>
                <li>
                    <label>
                        <div class="label">
                            Search Person
                        </div>
                        <input class="input autocomplete-off" 
                                id="adminPortalUserSearch" 
                                data-role="autocomplete" 
                                data-auto-bind="false" 
                                data-bind="source: userSearchDataSource" 
                                placeholder="Person Name..."
                                data-template="templateUserSearchAutocomplete" 
                                data-text-field="Name" />
                    </label>
                </li>
            </ul>
            <ul class="form-normal half-inputs no-margin">
                <li>
                    <label>
                        <div class="label">
                            Status
                        </div>
                        <input class="dropdown" 
                                id="adminPortalStatusFilter" 
                                data-role="dropdownlist" 
                                data-bind="value: selectedStatus, source: getAllStatusValues, events: { change: changeStatus }" 
                                data-option-label="All Statuses"
                                placeholder="All Statuses"
                                />
                    </label>
                </li>
            </ul>
            <div class="grid margin-top-more small"
                    data-scrollable="false" 
                    data-role='grid' 
                    data-bind='source: dsCompanies' 
                    data-sortable='true' 
                    data-pageable='{pageSizes: [5,10,20,30,50],buttonCount:4,alwaysVisible: false,responsive:false}' 
                    data-auto-bind='false'
                    data-columns='[{ title: "Name", field:"Name", template: "<a href=\"\" data-bind=\"text:Name, events:{click:clickCompanyName}\" title=\"View Company\"></a>"}, 
                                { title: "Status", field: "Status", template: "<span data-bind=\"text:Status, attr:{class:getCompanyStatusClass}\"></span>"}, 
                                { title: "Created", field: "DateCreated",format:"{0:d}" }, 
                                { title: "Joined", field: "JoinDate",format:"{0:d}" }, 
                                { title: "Paid Through", field: "PaidThrough",format:"{0:d}"}, 
                                { title: "Expired", field: "ExpiredDate",format:"{0:d}"}, 
                                { title: "Cancelled", field: "CancelDate" ,format:"{0:d}"}, 
                                { title: "Users", field: "ActivePayingUsers"}, 
                                { field: "CoachID", title: "Coach", width: 80,  template: "<button data-bind=\"visible:HasCoach, events:{click:clickCoach}\"><span class=\"icon align-2015 ico-coach\"></span></button>"}, 
                                {title: " ", field:"Comments", width: 38,  template: "<button data-bind=\"class:{activeComments:Comments}, events:{click:clickEditComments}\"><span class=\"icon ico-file\"></span></button>"}
                                ] '>
            </div>
        </div>
    </div>
    <!--#include file="/Templates/templateUserSearchAutocomplete.template.html"-->
    <!--#include file="/Templates/templateCoachDropdown.template.html"-->
    <!--#include file="/Templates/templateExternalPricePointDropdown.template.html"-->
    <script src="/js/Generated/SystemManageCompanies.js" type="text/javascript"></script>
    <script src="/js/Generated/AdminPortalUserSearch.js" type="text/javascript"></script>
</asp:Content>
