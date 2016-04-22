<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<%@taglib prefix="demo" tagdir="/WEB-INF/tags"%>

<c:url value="/data/project_menu/" var="transportReadUrl" />

<demo:header />
	<c:url value="/resources/web/panelbar/andrew.jpg" var="andrew" />
	<c:url value="/resources/web/panelbar/nancy.jpg" var="nancy" />
	<c:url value="/resources/web/panelbar/robert.jpg" var="robert" />
	
	<c:url value="/resources/web/panelbar/orgHead.png" var="orgHead" />
	<c:url value="/resources/web/panelbar/orgFoot.png" var="orgFoot" />

	<kendo:panelBar name="panelbar" expandMode="single">
		<kendo:panelBar-items>
			<c:forEach var="it" items="${projectlist}">
				<kendo:panelBar-item  text="Projects">
					<kendo:panelBar-items>
						<kendo:panelBar-item text="New Business Plan"/>
						<kendo:panelBar-item text="Sales Forecasts">
							<kendo:panelBar-items>
								<kendo:panelBar-item text="Q1 Forecast"/>
								<kendo:panelBar-item text="Q2 Forecast"/>
								<kendo:panelBar-item text="Q3 Forecast"/>
								<kendo:panelBar-item text="Q4 Forecast"/>		
							</kendo:panelBar-items>
						</kendo:panelBar-item>
						<kendo:panelBar-item text="Sales Reports"/>
					</kendo:panelBar-items>
				</kendo:panelBar-item>
			</c:forEach>
		</kendo:panelBar-items>
	</kendo:panelBar>
<demo:footer />


<style>
    #panelbar {
        max-width: 400px;
        margin: 0 auto;
    }
    .teamMate:after {
        content: ".";
        display: block;
        height: 0;
        line-height: 0;
        clear: both;
        visibility: hidden;
    }
    .teamMate h2 {
        font-size: 1.4em;
        font-weight: normal;
        padding-top: 20px;
    }
    .teamMate p {
        margin: 0;
    }
    .teamMate img {
        float: left;
        margin: 5px 15px 5px 5px;
        border: 1px solid #ccc;
        border-radius: 50%;
    }
</style>
<script type="text/javascript">
	/*
	var sharedDataSource = new kendo.data.DataSource({
		transport: {
			read: {
	    		url: "data-service.json",
				dataType: "json"
			}
		}
	});
	*/
</script>
