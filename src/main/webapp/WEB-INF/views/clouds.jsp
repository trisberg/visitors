<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="author" content="VMware" />
		<meta name="copyright" content="Copyright VMware 2011. All Rights Reserved." />
		<title>Cloud Foundry @ Visitors</title>
		<link rel="stylesheet" href="/resources/styles/main.css">
		<!--[if IE]><link rel="stylesheet" href="/resources/styles/ie.css"><![endif]-->
	</head>
	<body>
		<div class="header">
		    <div class="site-wrap">
		    	<div class="logo">
		           	<a href="http://www.cloudfoundry.com">Cloud Foundry</a>
	            </div>
				<div class="supernav">
					<div class="vmware">
						<a href="http://www.vmware.com" >VMware</a>
					</div>
					<div class="nav-menu">
					<a href="http://www.springsource.org/spring-data/mongodb">
						<img src="resources/images/spring-data-mongodb.png" width="450" alt="Spring Data MongoDB" /></a>
					</div>
	            </div>
			</div>
	    </div>
		<!-- Main Container Start-->
		<div id="main">
			<div class="container">
				<div class="content-wrap content">
					<div class="site-wrap">
						<h1>Clouds</h1>
	
						<h4>The visitor counts for all clouds:</h4>
						<table width="60%">
							<thead>
								<tr>
									<th width="60%"><u>Cloud Address</u></th>
									<th width="30%" align="right"><u>Visits</u></th>
									<th width="10%"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="cloud" items="${cloudList}">
									<tr>
										<td>${cloud.address}</td>
										<td>${cloud.visitCount}</td>
										<td>[ <a href="cloud/delete/${cloud.id}">X</a> ]</td>
									</tr>
								</c:forEach>
								<tr>
									<td>TOTAL</td>
									<td>${total}</td>
									<td></td>
								</tr>
							</tbody>
						</table>

		<p>&nbsp;</p>
		<p>&nbsp;</p>

		<form:form modelAttribute="cloud">
			<fieldset>
		        <div>
					<div>
						<label for="address">New Cloud Address:</label>
					</div>
					<div>
						<form:input class="span-9" path="address" />
					</div>
				</div>
				<div>
					<button type="submit" id="proceed" name="_proceed">Add</button>
				</div>
			</fieldset>
		</form:form>	

					</div>
				</div>
			</div>
		</div>
		<!-- Main Container End-->

	<!-- Footer Start-->
    <div class="footer">
      <div class="site-wrap">
        <div class="social-media-logos left">
          <a class="twitter" href="http://twitter.com/cloudfoundry" target="_blank">Twitter</a>
          <a class="facebook" href="http://www.facebook.com/cloudfoundry" target="_blank">Facebook</a>
          <a class="youtube" href="http://www.youtube.com/cloudfoundry" target="_blank">You Tube</a>
        </div>
        <div class="links right">
          <!-- <a href="http://www.cloudfoundry.com/faq" target="_blank">FAQ</a>
           |
           <a href="http://support.cloudfoundry.com/forums" target="_blank">Forums</a>
           |
           <a href="http://support.cloudfoundry.com/home" target="_blank">Support</a>
           |
           <a href="http://blog.cloudfoundry.com/" target="_blank">Blog</a>
           |
           <a href="http://www.cloudfoundry.com/terms" target="_blank">Terms</a>
           |
           <a href="http://www.cloudfoundry.com/legal" target="_blank">Legal</a>
           |
           <a href="http://www.vmware.com/help/privacy.html" target="_blank">Privacy</a> -->
 
          <div class="copyright">
           Copyright &copy; 2011. <a href="http://www.vmware.com/" target="_blank">VMware, Inc</a>. All rights reserved.
          </div>
        </div>
      </div>
    </div>		
	<!--Footer End-->

	</body>
</html>