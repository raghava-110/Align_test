<%@ Page Title="" Language="C#" MasterPageFile="~/MasterApplication.master" %>
<%@ OutputCache CacheProfile="Page"%>


<asp:Content ID="Content1" ContentPlaceHolderID="MasterApplicationContent" runat="server">
    <div id="content" data-bind='visibility: loaded' style="visibility: hidden;">
        <!-- Sticky Header -->
        <div class="content-header">
            <div class="content-title">Manage Users</div>
            <div class="subnav">
                <button class="button btn-filled" data-bind='click: clickInviteUser, visible:hasJoinedOrDoesntRequireDemo'>
                    <span class="icon ico-invite-team-feather"></span>
                    Invite Users
                </button>
                <button class="button btn-filled" data-bind='click: clickInviteViewers, visible:showInviteViewers'>
                    <span class="icon ico-eye"></span>
                    Invite Viewers
                </button>
                <button class="button btn-ghost" data-bind="click: clickUpdateMainCoach, displaynone: hasCoach">
                    <span class="icon ico-plus-circle"></span>
                    <span>Add Coach</span>
                </button>
                <button class="button btn-ghost" data-bind='click: clickUpdateMainCoach, display: hasCoach'>
                    <span class="icon align-2015 ico-coach"></span>
                    <span> Update Coach</span>
                </button>
                <button class="button btn-ghost" data-bind='click: clickInviteCoachingStaff, display: hasCoach'>
                    <span class="icon ico-invite-team-feather"></span>
                    <span>Invite Coaching Staff</span>
                </button>
                <button class="button btn-ghost" data-bind='click: clickSendAllOpenInvites, visible:showSendAllOpenInvites'>
                    <span class="icon ion-android-send"></span>
                    Send All Open Invites
                </button>
            </div>
        </div>
        
        <!-- <div data-bind="display: hasCoach">
            <ul>
                <li>
                    <h4>Coach</h4>
                    <span data-bind="text: coach.Name"></span>
                </li>
                <li>
                    <h4>Email Address</h4>
                    <span data-bind="text: coach.Email"></span>
                </li>
            </ul>
        </div> -->
        <div class="margin-top-more">
            <div class="module padded-wrapper">
                <div class="module-inner">
                    <h3>Current Users</h3>
                    <div class="form full">
                        <label>
                            <div class="label">Search Users</div>
                            <input class="input" type="text" data-bind='value: filter, events: { keyup: filterResultsAtPageOne }' placeholder="First or Last Name..." data-role='inputplaceholder' />
                        </label>
                    </div>

                    <div id="manage_users_users_grid"
                        class="grid fixed-table relative"
                        data-scrollable="false" 
                        data-role="grid" 
                        data-bind="source: dsCompanyUsers" 
                        data-sortable='true' 
                        data-pageable='{pageSizes: [5,10,20,30,50],buttonCount:4,alwaysVisible:false,responsive:false}'
                        data-auto-bind='false' 
                        data-columns='[
                            {title: "Login", field: "Email", width: 275},
                            {title: "First Name", field: "FirstName"}, 
                            {title: "Last Name", field: "LastName"},
                            {title: "Admin", field:"IsAdministrator", width: 100, headerAttributes: { style: "text-align: left"}, template:"<input data-role=\"uniform\" data-change-click-only=\"true\" data-bind=\"value:IsAdministrator, events:{change: clickToggleUserAdminPermissions}, disabled: IsViewer\" type=\"checkbox\" title=\"Check as Administrator\" />"}, 
                            {title: "Roles",  field: "", template: "<div class=\"flex\"><span data-bind=\"visible: Disabled\" title=\"The User is disabled, usually from too many failed login attempts. The User needs to reset their password before they will be able to log in.\"><span class=\"icon red ion-android-warning\"></span></span><span data-bind=\"visible: IsCoach\" title=\"Coach\"><span class=\"icon ico-coach active margin-left-less\"></span></span><span data-bind=\"visible: IsViewer\" title=\"View-only User\"><span class=\"icon ico-eye active margin-left-less\"></span></span><span data-bind=\"visible: IsAlignChampion\" title=\"Align Champion\"><span class=\"icon ico-award active margin-left-less\"></span></span><span data-bind=\"visible: IsDecisionMaker\" title=\"Decision Maker\"><span class=\"icon ion-compass active margin-left-less\"></span></span></div>"},
                            {title: "Last Login", field: "LastLogin", width: 125, format:"{0:d}"},
                            {title: "", template: "<div class=\"flex\"><button class=\"margin-left\" data-bind=\"click:clickResetPassword\" title=\"Reset this password\"><span class=\"icon ico-unlock\"></span></button><button class=\"margin-left\" data-bind=\"click:clickEditUserInfo\" title=\"Edit User Info\"><span class=\"icon ico-edit-\"></span></button><button class=\"delete margin-left\" data-bind=\"click:clickRemoveUserFromCompany\" title=\"Remove user from company\"><span class=\"icon ico-trash- delete\"></span></button></div>"}
                            ]'>

<%--                            {title: "", width: 30, template: "<button style=\"width: 20px;\" data-bind=\"click:clickResetPassword\" title=\"Reset this password\"><span class=\"icon ico-unlock\"></span></button>"},
                            {title: "", width: 30, template: "<button style=\"width: 20px;\" data-bind=\"click:clickEditUserInfo\" title=\"Edit User Info\"><span class=\"icon ico-edit-\"></span></button>"},
                            {title: "", width: 30, template: "<button style=\"width: 20px;\" class=\"delete\" data-bind=\"click:clickRemoveUserFromCompany\" title=\"Remove user from company\"><span class=\"icon ico-trash- delete\"></span></button>"}--%>

                    </div>
                </div>
            </div>

            <div class="module padded-wrapper" data-bind="showProgress:showProgress">
                <div class="module-inner">
                    <h3>Pending Invitations</h3>
                    <div class="grid fixed-table relative"
                         data-scrollable="false" 
                         data-role='grid' 
                         data-bind='source: dsCompanyInvitations, visible: userRolesViewerFeatureEnabled' 
                         data-sortable='true' 
                         data-pageable='{pageSizes: [5,10,20,30,50],buttonCount:4,alwaysVisible:false,responsive:false}' 
                         data-auto-bind='false'
                         data-columns='[{title: "Email Address", field: "EmailAddress"},
                            {title: "Last Sent", field: "DateLastSent", format:"{0:G}"},
                            {title: "Date Declined", field: "DateRejected",format:"{0:d}"},
                            {title: "Roles", width: 90, template: "<div class=\"flex\"><span class=\"icon ion-android-checkbox active\" data-bind=\"visible:IsAdministrator\" title=\"Administrator\"></span><span data-bind=\"visible: IsViewer\" title=\"View-only User\"><span class=\"icon ico-eye active\"></span></span><span data-bind=\"visible: IsCoachingStaff\" title=\"Coaching Staff\"><span class=\"icon ico-coach active margin-left-less\"></span></span></div>"},
                            {width: 60, template: "<img class=\"profile-pic-small ensure-34px-width\" src=\"#=CreatedByPhotoURL#&width=34&height=34\" title=\"Created by #=CreatedBy#\" loading=\"lazy\">"}, 
                            {title: "", template: "<div class=\"flex\"><button class=\"margin-left-more\" data-bind=\"click:clickResendInvite, invisible:coachedAndCoachingCompanyThatRequiresDemo\" title=\"Resend Invite\"><span class=\"icon ion-loop\"></span></button><button class=\"approve margin-left-more\" data-bind=\"click:clickAcceptInviteForUser, invisible:coachedAndCoachingCompanyThatRequiresDemo\" title=\"Accept Invite\"><span class=\"icon ion-checkmark-circled\"></span></button><button class=\"delete margin-left-more\" data-bind=\"click:clickDeleteInvite\" title=\"Delete Invite\"><span class=\"icon ico-trash- delete\"></span></button></div>"}
                            ]'>
<%--                            {title: "", width: 30, template:"<button style=\"width: 10px;\" data-bind=\"click:clickResendInvite, invisible:coachedAndCoachingCompanyThatRequiresDemo\" title=\"Resend Invite\"><span class=\"icon ion-loop\"></span></button>"},
                            {title: "", width: 30, template:"<button style=\"width: 10px;\" class=\"approve\" data-bind=\"click:clickAcceptInviteForUser, invisible:coachedAndCoachingCompanyThatRequiresDemo\" title=\"Accept Invite\"><span class=\"icon ion-checkmark-circled\"></span></i></button>"},
                            {title: "", width: 30, template:"<button style=\"width: 10px;\" class=\"delete\" data-bind=\"click:clickDeleteInvite\" title=\"Delete Invite\"><span class=\"icon ico-trash-\"></span></button>"}--%>

                    </div>
                    <div class="grid fixed-table relative"
                         data-scrollable="false" 
                         data-role='grid' 
                         data-bind='source: dsCompanyInvitations, invisible: userRolesViewerFeatureEnabled' 
                         data-sortable='true' 
                         data-pageable='{pageSizes: [5,10,20,30,50],buttonCount:4,alwaysVisible:false,responsive:false}' 
                         data-auto-bind='false'
                         data-columns='[{title: "Email Address", field: "EmailAddress"},
                            {title: "Date Created", field: "CreatedDate", format:"{0:d}"},
                            {title: "Last Sent", field: "DateLastSent", format:"{0:G}"},
                            {title: "Date Declined", field: "DateRejected",format:"{0:d}"},
                            {title: "Roles", width: 90, field: "", template: "<div class=\"flex\"><span class=\"icon ion-android-checkbox active\" data-bind=\"visible:IsAdministrator\" title=\"Administrator\"></span><span data-bind=\"visible: IsViewer\" title=\"View-only User\"><span class=\"icon ico-eye active\"></span></span><span data-bind=\"visible: IsCoachingStaff\" title=\"Coaching Staff\"><span class=\"icon ico-coach active margin-left-less\"></span></span></div>"},
                            {width: 60, template: "<img class=\"profile-pic-small ensure-34px-width\" src=\"#=CreatedByPhotoURL#&width=34&height=34\" title=\"Created by #=CreatedBy#\" loading=\"lazy\">"}, 
                            {title: "", template: "<div class=\"flex\"><button class=\"margin-left-more\" data-bind=\"click:clickResendInvite, invisible:coachedAndCoachingCompanyThatRequiresDemo\" title=\"Resend Invite\"><span class=\"icon ion-loop\"></span></button><button class=\"approve margin-left-more\" data-bind=\"click:clickAcceptInviteForUser, invisible:coachedAndCoachingCompanyThatRequiresDemo\" title=\"Accept Invite\"><span class=\"icon ion-checkmark-circled\"></span></button><button class=\"delete margin-left-more\" data-bind=\"click:clickDeleteInvite\" title=\"Delete Invite\"><span class=\"icon ico-trash- delete\"></span></button></div>"}
                            ]'>
<%--                            {title: "", width: 30, template:"<button style=\"width: 10px;\" data-bind=\"click:clickResendInvite, invisible:coachedAndCoachingCompanyThatRequiresDemo\" title=\"Resend Invite\"><span class=\"icon ion-loop\"></span></button>"},
                            {title: "", width: 30, template:"<button style=\"width: 10px;\" class=\"approve\" data-bind=\"click:clickAcceptInviteForUser, invisible:coachedAndCoachingCompanyThatRequiresDemo\" title=\"Accept Invite\"><span class=\"icon ion-checkmark-circled\"></span></i></button>"},
                            {title: "", width: 30, template:"<button style=\"width: 10px;\" class=\"delete\" data-bind=\"click:clickDeleteInvite\" title=\"Delete Invite\"><span class=\"icon ico-trash-\"></span></button>"}--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--#include file="/Templates/templateUserRoleEdit.template.html"-->
    <script src="/js/Generated/ManageUsers.js" type="text/javascript"></script>
</asp:Content>
