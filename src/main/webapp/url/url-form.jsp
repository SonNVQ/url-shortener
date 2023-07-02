<%-- 
    Document   : url-form
    Created on : Jun 23, 2023, 11:07:56 PM
    Author     : nguyenson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Url</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
            rel="stylesheet"
            />
        <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
            />
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.3.1/mdb.min.css"
            rel="stylesheet"
            />
        <style>
            .input-title {
                font-size: 1.05rem;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-6">
                    <h1>Add url</h1>
                    <form action="${pageContext.request.contextPath}/url/add" method="post">
                        <!-- Email input -->
                        <div class="form-outline mb-4">
                            <input type="text" name="link" id="link" class="form-control" />
                            <label class="form-label" for="link">Link</label>
                        </div>
                        <div class="form-outline mb-4">
                            <input type="text" name="uid" id="uid" class="form-control" />
                            <label class="form-label" for="uid">Custom short url</label>
                        </div>
                        <div class="form-outline mb-4">
                            <input type="text" name="passcode" id="passcode" class="form-control" />
                            <label class="form-label" for="passcode">Pass code</label>
                        </div>
                        <div class="form-outline mb-4">
                            <input type="number" name="redirect-time" min="1" max="30" id="redirect-time" class="form-control" />
                            <label class="form-label" for="redirect-time">Redirect time</label>
                        </div>
                        <div class="form-outline mb-4">
                            <input type="text" name="redirect-message" id="redirect-message" class="form-control" />
                            <label class="form-label" for="redirect-message">Redirect message</label>
                        </div>

                        <div class="input-title">Expiration Time</div>
                        <div class="form-outline mb-4">
                            <input type="datetime-local" name="expiration-time" id="datetimepicker" class="form-control" />
                            <!--<label class="form-label" for="expiration-time">Expiration Time</label>-->
                        </div>

                        <!-- Submit button -->
                        <button type="submit" class="btn btn-primary btn-block">Fly</button>
                    </form>
                    <p>Link: ${url}</p>
                </div>
            </div>
        </div>

        <script
            type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.3.1/mdb.min.js"
        ></script>
    </body>
</html>
