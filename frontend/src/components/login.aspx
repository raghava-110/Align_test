<%@ Page Title="" Language="C#" MasterPageFile="~/Master.master" %>

<%@ OutputCache CacheProfile="Page" %>
<asp:Content ID="Content1" ContentPlaceHolderID="MasterContent" runat="server">
    <!--
    this uses master.master and not masterpublic, because we should not autohide the loader until autologin fires on this single page.
    GRIPPO
    -->
    <div id="swappableContentMasterPublic" class="masterContainer external">
        <div class="publicContainer">

            <div id="content" data-bind="showProgress: showProgress">
                <div class="login module">
                    <div class="login-inner">
                        <div id="default_logo_login_div" class="logo-login standard-padding">
                            <img data-bind="attr: {src: getAppLogo, alt: getAppLogoAltText}"  alt="Align" src="/img/align-logo.svg"/>
                            <div style="display: none; width: 100%; padding-left: 33%;" data-bind="visible:showPoweredByAlign">
                                <div style="float: left; font-size:small; padding-top: 3px; padding-right: 5px;">powered by</div>
                                <img style="width: 60px;" src="/img/align-logo.svg" alt="Powered By Align"/>                               
                            </div>
                        </div>
                        <ul class="form full no-style" data-bind="invisible:mfaVerificationMode">
                            <li>
                                <label>
                                    <span class="label">Email
                                    </span>
                                    <input id="usernameField" class="input" placeholder="email@address.com" name="username" data-bind="value: username, events: { keyup: emailKeyUp }" type="email" />
                                </label>
                            </li>
                            <li>
                                <div class="center-content-vertically" data-bind="visible: showContinueButton">
                                    <button class="login-button" data-bind="click: clickPerformEmailLookup">
                                        Continue
                                    </button>
                                </div>
                            </li>
                            <li class="fs-block" style="display: none;" data-bind="visible: showPasswordField">
                                <label class="fs-block">
                                    <span class="label">Password
                                    </span>
                                    <input id="passwordField" class="input fs-block" placeholder="Password" name="password" data-bind="value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }" type="password" />
                                    <input class="input fs-block" placeholder="Password" name="password" data-bind="value: password, display: showClearText" type="text" style="display: none;" />
                                </label>
                            </li>
                            <li style="display: none;" data-bind="visible: showPasswordField">
                                <div class="center-content-vertically">
                                    <button class="login-button" data-bind="click: clickLogin">
                                        Login
                                    </button>
                                </div>
                            </li>
                            <li style="display: none;" data-bind="visible: showLoginDivider">
                                <div class="login-divider-wrapper">
                                    <hr class="login-divider"/>
                                    <span> or continue with: </span>
                                    <hr class="login-divider"/>
                                </div>
                            </li>
                            <li style="display: none;" data-bind="visible: SSOEnabled">
                                <div class="login-wrapper">
                                    <button class="google-sign-in-button" data-bind="click: clickLoginWithGoogle">
                                        <span class="google-icon"></span>
                                        <span class="button-text"> Google</span>
                                    </button>
                                </div>
                            </li>
                            <li style="display: none;" data-bind="visible: SSOMicrosoftEnabled">
                                <div class="login-wrapper">
                                    <button class="microsoft-sign-in-button" data-bind="click: getTokenIDFromMicrosoft">
                                        <span class="microsoft-icon"></span>
                                        <span class="button-text"> Microsoft</span>
                                    </button>
                                </div>
                            </li>
                        </ul>
                        <ul class="form full no-style" style="display: none" data-bind="visible:mfaVerificationMode">
                            <li>
                                <label>
                                    <span>A Verification Code has been sent as</span>
                                    <span data-bind="text: mfaVerificationCodeSourceTypeText"> an email</span>
                                    <span> to </span>
                                    <strong data-bind="text: mfaVerificationCodeSourceID"></strong>
                                    <p>Locate the message and enter the code below:</p>
                                </label>
                            </li>
                            <li>
                                <label>
                                    <span class="label">Verification Code
                                    </span>
                                    <input class="input" name="mfa_verification_code" data-bind="value: mfaVerificationCode, events: { keyup: verificationCodeKeyUp }" />
                                </label>
                            </li>
                            <li>
                                <!--2022-09-16 RJK: Removed enabled binding th the mfaVerificationCode Value because the paste doesn't immediately update the value so the button was disabled after paste, in some cases. -->
                                <button class="button green big" data-bind="click: clickVerifyMFACode">
                                    Verify Code
                                </button>
                            </li>
                        </ul>
                        <div class="form-lower margin-top-more" data-bind="invisible:mfaVerificationMode">
                            <label style="display: none;" class="checker-wrapper block" data-bind="visible: showShowPassword">
                                <input data-role="uniform" type="checkbox" data-bind="value: showClearText" />
                                <span class="label">Show Password
                                </span>
                            </label>
                            <label class="checker-wrapper block" style="display: none;" data-bind="visible: showRememberMe">
                                <input data-role="uniform" type="checkbox" data-bind="value: rememberMe" />
                                <span class="label">Always Remember Me
                                </span>
                            </label>
                        </div>
                        <div class="login-links center" style="display: none;" data-bind="visible:mfaVerificationMode">
                            <button class="no-style button" data-bind="click: clickSignOut">Sign Out</button>
                        </div>
                        <div class="login-links" style="display: none;" data-bind="invisible:mfaVerificationMode">
                            <div data-bind="visible: showForgotPassword">
                                <a data-role="link" data-bind="href:getBrandedForgotPasswordHref" href="/PublicApplication/ForgotPassword.aspx" data-swaptarget="swappableContentMasterPublic">Forgot Password?
                                </a>
                            </div>
                            <div class="demo-signup-message">
                                <span data-bind="text:getDemoSignupMessage">Not on Align? </span><a data-role="link" data-bind="href:getDemoSignupHref" href="https://aligntoday.com/product/request-demo/"> Request a demo to get started.
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script src="/js/Generated/Default.js"></script>
        </div>
    </div>
</asp:Content>
