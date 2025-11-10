<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPopup.Master" %>

<%@ OutputCache CacheProfile="Page" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MasterContent" runat="server">
    <div class="pop-wrapper" id="contentPopup" data-bind="showProgress: showProgress">
        <div class="pop-content">
            <div data-bind="visible: loaded">
                <ul class="form-normal">
                    <li>
                        <label>
                            <div class="label">
                                Company Name
                            </div>
                            <input class="input autocomplete-off" placeholder="Company Name..." type="text" data-bind="value: company.Name" />
                        </label>
                    </li>
                    <li data-bind="visible: isNewCompany" style="display: none">
                        <label>
                            <div class="label">
                                Align Status
                            </div>
                            <input class="dropdown" 
                                data-role="dropdownlist" 
                                placeholder="Select Prospect or Trial..." 
                                data-option-label="Select Prospect or Trial..." 
                                data-bind="value: company.Status, 
                                            source: getStartingStatusValues,
                                            events: {
                                                        change: changeStatusValue
                                                    }"
                            />
                        </label>
                    </li>
                    <li>
                        <label>
                            <div class="label">
                                Branding
                            </div>
                            <input class="dropdown" data-option-label="Select a Branding.." data-role="dropdownlist" data-text-field="Name" data-value-field="ID" data-bind="source: dsAlignBrands, value: company.AlignBrand, events: { change: changeAlignBrand }" />
                        </label>
                    </li>
                    <li>
                        <label>
                            <div class="label">
                                Company Size (number of employees)
                            </div>
                            <input class="input" placeholder="Company Size..." data-role="numerictextbox" data-min="1" data-format="n0" data-decimals="0" data-bind="value: company.TeamMemberCount" />
                        </label>
                    </li>
                    <li>
                        <label>
                            <div class="label">
                                Leadership Team Size
                            </div>
                            <input class="input" placeholder="Leadership Team Size..." data-role="numerictextbox" data-min="1" data-format="n0" data-decimals="0" data-bind="value: company.LeadershipTeamMemberCount" />
                        </label>
                    </li>
                    <li>
                        <label>
                            <div class="label">
                                Annual Revenue
                            </div>
                            <input class="input" placeholder="Annual Revenue..." data-role="numerictextbox" data-min="1" data-format="c0" data-decimals="0" data-bind="value: company.Revenue" />
                        </label>
                    </li>
                    <li id="scaling_up_experience" data-bind="visible: isScalingUpBrand" style="display: none;">
                        <label>
                            <div class="label">How experienced is the team with Scaling Up?</div>
                            <select class="dropdown create-account-reason"
                                data-option-label="Pick the team's Scaling Up experience level" 
                                data-role='dropdownlist' 
                                data-bind="value: ScalingUpExperience">
                                <option value="Beginner">Just starting</option>
                                <option value="Intermediate">Using some of the habits</option>
                                <option value="Expert">Pros. Verne would be proud!</option>
                                <option value="Unknown">Unknown</option>
                            </select>
                        </label>
                    </li>
                    <li>
                        <label>
                            <div class="label">What challenges is the Company looking to solve?</div>
                            <select class="dropdown create-account-reason"
                                data-placeholder="Pick one or more challenges" 
                                data-role='multiselect' 
                                data-bind="value: ChallengesToSolve">
                                <option value="Finish more Strategic Items">Finish more strategic items</option>
                                <option value="Dashboard Visualizations">Visualize numbers with dashboards</option>
                                <option value="Strategy and Effective Communication">Effectively communicate the strategy</option>
                                <option value="Personal Impact">Highlight personal impact</option>
                                <option value="Team Impact">Understand a team's impact on the Company</option>
                                <option value="No idea">No idea!</option>
                            </select>
                        </label>
                    </li>
                    <li>
                        <label>
                            <div class="label">What tools does the Company use to work on these challenges today?</div>
                            <div class="textarea auto-scroll-text-area"
                                    data-role="aligneditor"
                                    data-default-text="What do we know about the tools the Company uses today? This is also a great place to put any other notes about what they're trying to accomplish and how we can help them achieve their goals."
                                    data-bind="value: company.ExistingToolsAndNotes">
                            </div>
                        </label>
                    </li>
                    <li>
                        <label>
                            <div class="label">
                                Preferred Management Framework
                            </div>
                            <input class="dropdown" data-option-label="Select a management framework..." data-role="dropdownlist" data-bind="source: dsManagementFrameworks, value: company.PreferredManagementFramework" />
                        </label>
                    </li>
                </ul>
                <ul class="form-normal">
                    <li>
                        <label>
                            <div class="label">
                                Promo Code
                            </div>
                            <input class="input" placeholder="Promo Code..." type="text" data-bind="value: promoCode" />
                        </label>
                    </li>
                    <li>
                        <label>
                            <div class="label">
                                Affiliate 
                            </div>
                            <input class="input" placeholder="Enter an Affiliate ID..." type="text" data-bind="value: company.AffiliateFromPersonID" />
                        </label>
                    </li>
                    <li>
                        <label>
                            <div class="label">
                                Coach 
                            </div>
                            <input class="input autocomplete-off"
                                    data-role="autocomplete"
                                    data-placeholder="Find a Coach" 
                                    data-value-primitive="false"
                                    data-text-field="Name"
                                    data-filter="contains"
                                    data-minLength="2"
                                    data-template="coachDropdownTemplate"
                                    data-bind="value: company.Coach, source: dsCoaches"
                                    style="width: 100%;" />
                        </label>
                    </li>
                </ul>
                <ul class="form-normal">
                    <li>
                        <label>
                            <div class="label">
                                External Product and Custom Price Point
                            </div>
                            <input class="dropdown"
                                    placeholder="Select a Product and Price Point Handle..."
                                    data-option-label="Select a Product and Price Point..."
                                    data-role="dropdownlist"
                                    data-text-field="ProductAndPricePointHandle"
                                    data-value-field="ProductAndPricePointHandle"
                                    data-template="externalProductAndPricePointDropdownTemplate" 
                                    data-bind="value: externalProductAndPricePoint, source: dsExternalPricePoints" />
                        </label>
                    </li>
                    <li>
                        <input 
                            id="company.ShowExternalSignupPageURLInCompany" 
                            data-role="uniform" 
                            type="checkbox"  
                            data-change-click-only="true" 
                            data-bind="value: company.ShowExternalSignupPageURLInCompany" />
                        <label class="label" for="company.ShowExternalSignupPageURLInCompany">Show External Signup Page Link to Company</label>
                    </li>
                </ul>
            </div>
            <div class="button-footer">
                <div class="button-tray">
                    <button class="button green" data-bind="click: clickSaveCompany">
                        <span class="icon ion-checkmark-circled"></span>
                        <span>Save
                        </span>
                    </button>
                </div>
                <div class="cancel-actions">
                    <button class="cancel" data-bind="click: clickCancel">
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    </div>
    <script src="/js/Generated/SystemAdminCreateCompanyDialog.js" />
</asp:Content>
