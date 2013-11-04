<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<span class="contentNewsTitle"><html:link action="/products">
		<bean:message key="label.products.products" />
	</html:link></span>
&gt;&gt;
<bean:message key="label.products.productslist" />
<br>
<br>
<logic:empty name="productForm" property="categoryList">
	<bean:message key="label.products.noproducts" />
</logic:empty>
<logic:notEmpty name="productForm" property="categoryList">
	<logic:iterate id="category" property="categoryList" name="productForm">
		<table id="products">
			<tr>
				<td id="category"><bean:write name="category" property="name" /></td>
			</tr>
			<logic:notEmpty name="category" property="subcategories">
				<logic:iterate id="subcategory" property="subcategories"
					name="category">
					<tr>
						<td id="subcategory"><bean:write name="subcategory"
								property="name" /></td>
					</tr>
					<logic:notEmpty name="subcategory" property="products">
						<logic:iterate id="product" property="products" name="subcategory">
							<tr>
								<td class="product"><bean:write name="product"
										property="productName" /></td>
								<td class="product"><bean:write name="product"
										property="provider" /></td>
								<td class="product"><bean:write name="product"
										property="model" /></td>
								<td class="product"><bean:write name="product"
										property="color" /></td>
								<td class="product"><bean:write name="product"
										property="dateOfIssue" formatKey="date.pattern" /></td>
								<c:choose>
									<c:when test="${product.notInStock == true }">
										<td class="product"><bean:message
												key="label.products.notinstock" /></td>
									</c:when>
									<c:otherwise>
										<td class="product"><bean:write name="product"
												property="price" /></td>
									</c:otherwise>
								</c:choose>
							</tr>
						</logic:iterate>
					</logic:notEmpty>
				</logic:iterate>
			</logic:notEmpty>
		</table>
	</logic:iterate>
</logic:notEmpty>
<br>