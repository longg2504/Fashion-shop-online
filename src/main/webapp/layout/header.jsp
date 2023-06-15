<%--
  Created by IntelliJ IDEA.
  User: TechCare
  Date: 14/06/2023
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
<!-- Header -->
<header class="">
    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-left">
                    <li class="nav-item">
                        <a class="nav-link" href="./index.jsp">Home
                        </a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="products.php?per_page=9&page=1&type=all" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">Products</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="products.php?per_page=9&page=1&type=all">ALL</a>
                            <a class="dropdown-item" href="products.php?per_page=9&page=1&type=hoodie">Hoodie</a>
                            <a class="dropdown-item" href="products.php?per_page=9&page=1&type=jacket">Jacket</a>
                            <a class="dropdown-item" href="products.php?per_page=9&page=1&type=pants">Pants</a>
                            <a class="dropdown-item" href="products.php?per_page=9&page=1&type=shirt">Shirt</a>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="checkout.php">Cart</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="contact.php" role="button"  data-bs-toggle="dropdown" aria-expanded="false">More</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="contact.php">Contact</a>
                            <a class="dropdown-item" href="about-us.php">About us</a>
                        </div>
                    </li>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="information.php" drole="button"  data-bs-toggle="dropdown" aria-expanded="false"></a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="information.php">Information</a>
                            <a class="dropdown-item" href="history.php">History</a>
                            <a class="dropdown-item" href="changePassword.php">Change password</a>
                            <hr class="dropdown-divider">
                            <a class="dropdown-item" href="logout.php">Sign-out</a>
                        </div>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="sign.php">Sign-In</a></li>
                </ul>
            </div>
            <a class="navbar-brand" href="index.php">
                <h2>Clothers<em> Store</em></h2>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
    </nav>
</header>
</body>
</html>