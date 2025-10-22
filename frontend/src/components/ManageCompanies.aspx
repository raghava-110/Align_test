<%@ Page Title="" Language="C#" MasterPageFile="~/MasterApplicationNoHeader.master" %>
<%@ OutputCache CacheProfile="Page"%>


<asp:Content ID="Content1" ContentPlaceHolderID="MasterContent" runat="server">
    <div id="content" class="masterContainer applicationContainerOuter">
        <nav class="nav navHeader">
            <div class="nav-top manage-header">
                <div>
                    <img class="avatar icon" src="#" data-bind="attr: { src: currentPerson.Picture }" />
                    <h5>
                        Welcome <span data-bind="text: currentPerson.Name"></span>!
                    </h5>
                </div>
                <button class="button small orange" data-bind="click: clickLogOut">
                    Sign Out
                </button>
            </div>
        </nav>
        <main class="applicationContainerMain">
            <div class="main-inner">
                <div class="company-cards" data-role="listview" data-template="templateCompanyCard" data-bind="source: dsCompanies"></div>      
                <%--data-scrollable is invalid after Kendo Update, behavior is still the same without attribute 10/26/23--%>
                <%-- <div  class="company-cards" data-role="listview" data-template="templateCompanyCard" data-scrollable="endless" data-bind="source: dsCompanies"></div>--%>
            </div>
        </main>
        <div class="padded">
            <div data-bind="display: showCreateCompanyButton">
                <button style="float:right" class="button green big margin-left-less" data-bind="click: clickCreateCompany">
                    <span class="icon ico-plus-circle"></span>
                    Create a Company
                </button>
                <button style="float:right" class="button big green ghost margin-right" data-bind="click: clickCreateSandbox, display: showCreateSandboxButton">
                    <span class="icon ico-plus-circle"></span>
                    Create a Sandbox
                </button>
            </div>
            <div class="manage-companies-message center" data-bind="display: showContactAlign">
                Contact your Advisor at (888) 315-4049 or Advisor@aligntoday.com if you would like to create another company for your account.
            </div>
        </div>
    </div>

    <!--#include file="/Templates/templateManageCompanyCard.template.html"-->
    <script src="/js/Generated/ManageCompanies.js" type="text/javascript"></script>
</asp:Content>
