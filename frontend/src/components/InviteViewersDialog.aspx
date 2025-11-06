<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPopup.Master" %>

<%@ OutputCache CacheProfile="Page" %>


<asp:Content ID="Content1" ContentPlaceHolderID="MasterContent" runat="server">
    <div class="pop-wrapper" id='inviteViewersContentPopup' data-bind='showProgress: showProgress'>
        <div class="pop-content overflow-list">
            <h2 style="margin-bottom: 13px !important;">
                <span class="icon ico-eye brand-color margin-right-less" style="font-size: 20px;"></span>
                <span>Invite Viewers</span>
            </h2>
            <p>Viewers can see information that is available to everyone and can be invited into Huddles to view information that is available to those Huddles and Teams.</p>
            <p><strong>Note: </strong>Viewers can't make any changes to information in the system.</p>
            <p data-bind="visible:companyIsInTrialAndRequiresDemo"><em>Invitations will not be sent until this account subscribes.</em></p>
            <p data-bind="visible:onlyChargeForAdmins">
                Your pricing plan only charges for Administrators.<br />
                Invitations to Viewers will not be charged.
            </p>
            <div class="invite-list k-widget k-listview k-listview-bordered no-padding-bottom">
                <div class="k-listview-content">
                    <ul class="full form no-style no-margin-bottom" data-uid="eeee3e21-de9a-4535-bb2c-696c9399c14d" role="listitem" style="margin-bottom: 0;">
                        <li class="no-margin-bottom">
                            <div class="flex new-viewer-email-input float-left">
                                <span class="label newuseremaillabel newuserdialoglabel float-left">Viewer Email Address</span>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="invite-list" id="inviteViewersList"
                data-role="listview"
                data-template="templateInviteViewerEntry"
                data-bind="source: dsUserInvites">
            </div>

            <button id="inviteViewersContentPopup_button_addInvite" class="small button btn-ghost" data-bind="click: clickAddInvite"><span class="icon ico-plus-circle margin-right-less"></span>Add Another Viewer's Email</button><br /><span class="smaller-font">(or press the Return/Enter key to quickly add more viewers)</span>

            <label class="multiselect-no-tags" data-bind="visible: showAddToHuddles">
                <div class="label margin-top" title="Select one or more Huddles...&#013;&#010;The invited Viewer(s) will be added to the Huddle as a Viewer.">
                    Add to Huddle(s) - Optional
                </div>
                <select
                    id="invite-huddles-multiselect"
                    data-role="multiselect"
                    multiple="multiple"
                    data-auto-bind="false"
                    data-item-template="huddleInviteTemplate"
                    data-filter="contains"
                    data-value-field= "ID"
                    data-text-field="Name"
                    data-placeholder="Select one or more huddles..."
                    data-bind="value: selectedHuddles, source: dsHuddles, events: { change: changeSelectedHuddles, dataBound: updateSelectedHuddlesDisplayed, close: updateSelectedHuddlesDisplayed}">
                </select>
            </label>

            <div data-bind="display: hasSubscription" style="display: none;">
            </div>
        </div>

        <div data-bind="display: hasSubscription" style="display: none;">
            <div class="pop-content">
                <ul class="full form no-style">
                    <li>
                        <div class="label">Promo Code</div>
                    </li>
                    <li>
                        <div class="flex promo-code">
                            <input id="inviteViewersContentPopup_input_promoCode" class="input" type="text" data-bind="value: promoCode.code" />
                            <button id="inviteViewersContentPopup_button_apply" class="small button gray" data-bind="text: promoCode.buttonCaption, click: promoCode.clickApply">Apply</button>
                        </div>
                    </li>
                    <li>
                        <h4 class="red" data-bind="visible: promoCode.invalidCode">
                            <span class="icon ion-android-warning"></span>
                            <span data-bind="text: promoCode.reason">Invalid Promo Code</span>
                        </h4>
                        <h4 class="green" data-bind="visible: promoCode.applied">
                            <span data-bind="text: promoCode.reason">Invalid Promo Code</span>
                        </h4>
                    </li>
                </ul>
            </div>
            <!--2024-02-16 RJK: Hide the Price Estimate section when Company is on Coach Master License Program -->
            <div class="summary-footer"
                    data-align-feature-flag="Administration.ManageSubscription.Hide" 
                    data-align-feature-flag-behavior="hidden">
                <!--Billing Info Summary -->
                <div class="module padded-wrapper flexSmall">
                    <div>
                        <div>
                            <div class="module-actions">
                                <button class="button no-style margin-top-neg-three" data-bind="click: clickUpdate">
                                    <span class="icon ico-edit-"></span>
                                </button>
                            </div>
                            <h3>Billing </h3>
                            <ul data-bind="displaynone: subscriptionLoading" class="no-style">
                                <li>
                                    <span data-bind="text: subscription.BillingInfo.CreditCard.CCPartial"></span>
                                </li>
                                <li>
                                    <span class="ccExpiration" data-bind="css: { expired: isCreditCardExpired }, attr: { title: getCCExpirationTitleText }">Expires <span data-bind="text: subscription.BillingInfo.CreditCard.CCExpiration"></span></span>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!--<li data-bind="text: subscription.BillingInfo.CCName"></li>-->
                </div>
                <!--Price Estimate-->
                <div class="module padded-wraper flexBig">
                    <div class="flex column">
                        <div class="summary inviteSummary">
                            <h3>Order Summary</h3>
                            <div class="inviteSummaryLoading" data-bind="display: orderSummaryLoading">
                                Calculating...
                            </div>
                            <div data-bind="displaynone: orderSummaryLoading" style="display: none;">
                                <div class="line">
                                    <div class="sum-description">Rate </div>
                                    <!--the $ is cardcoded, because verything is USD-->
                                    <div class="sum-item">$<span data-bind="text: allocationChangePreview.ProratedCostNow" data-format="#.00"></span></div>
                                </div>
                                <div class="line">
                                    <div class="sum-description">Number of Chargeable Invites </div>
                                    <div class="sum-item"><span data-bind="text: allocationChangePreview.LicensesAdded"></span></div>
                                </div>
                                <div class="line" data-bind="display: subscription.CurrentAccountCredit" style="display: none;">
                                    <div class="sum-description credit">Account Credit </div>
                                    <div class="sum-item credit">
                                        <!--the $ is cardcoded, because verything is USD-->
                                        $<span data-bind="text: subscription.CurrentAccountCredit" data-format="#.00"></span>
                                        <span class="padding-left-less" data-role="helpbutton" data-helpname="CurrentAccountCredit"></span>
                                    </div>
                                </div>
                                <div class="sum-line"></div>
                                <div class="line total">
                                    <div class="sum-description">Total </div>
                                    <div class="sum-item">
                                        <!--the $ is cardcoded, because verything is USD-->
                                        $<span data-bind="text: getAmountToCharge" data-format="#.00"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <div class="button-footer margin-top-more">
        <div class="button-tray">
            <button class="button btn-filled" data-bind='click: clickSendInvite, invisible: loading, disabled: validatingEmail'>
                <span class="icon ico-eye"></span>
                <span data-bind="invisible: validatingEmail">
                    <span data-bind="display: includePurchaseCaptionOnSendInviteButton" style="display: none;">Purchase and </span>
                    Send Viewer Invite(s)
                </span>
                <span data-bind="visible: validatingEmail">
                    Validating Invite...
                </span>
            </button>
        </div>
        <div class="cancel-actions">
            <button class="cancel" data-bind='click: clickCloseDialog'>
                Cancel
            </button>
        </div>
    </div>

    </div>

    <script type="text/javascript" src="/js/Generated/InviteViewersDialog.js"></script>
    <!--#include file="/Templates/templateInviteViewerEntry.template.html"-->
    <!--#include file="/Templates/Invite-AssignHuddle.template.html" -->
</asp:Content>
