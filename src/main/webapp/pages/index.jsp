<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Landing Page</title>
    <link rel="stylesheet" href="style.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
</head>
<body>
<!-- Header -->
<header>
    <span class="year">2018 - 2023</span>
    <a href="#" class="logo"><img src="images/logo.png" alt="logo"></a>
    <a href="#" class="menu" onclick="toggleMenu();"></a>
</header>
<div class="banner">
    <ul class="nav">
        <li><a href="#">Supercars</a> </li>
        <li><a href="#">GT</a> </li>
        <li><a href="#">Ultimate</a> </li>
        <li><a href="#">Solus GT</a> </li>
        <li><a href="#">Legacy</a> </li>
    </ul>
    <div class="bg-video-list">
        <video class="bg-video mclaren1 active" src="images/mclaren-1.mp4" autoplay loop muted></video>
        <video class="bg-video mclaren2" src="images/mclaren-2.mp4" autoplay loop muted></video>
        <video class="bg-video mclaren3" src="images/mclaren-3.mp4" autoplay loop muted></video>
        <video class="bg-video mclaren4" src="images/mclaren-4.mp4" autoplay loop muted></video>
        <video class="bg-video mclaren5" src="images/mclaren-5.mp4" autoplay loop muted></video>
    </div>
    <div class="content">
        <h1>McLAREN</h1>
        <h3 class="model mclaren-1 active ">765LT SPIDER</h3>
        <h3 class="model mclaren-2" style="color: #92ff00;">ARTURA</h3>
        <h3 class="model mclaren-3" style="color: #ff0015;">7505</h3>
        <h3 class="model mclaren-4" style="color: #ff2e00;">765LT</h3>
        <h3 class="model mclaren-5" style="color: #bbc0dd;">7505 SPIDER</h3>
        <h2>Experience the Thrill</h2>
        <p>Join us to explore the world of high-performance cars.</p>
        <div class="button-container">
            <%
            String username = (String) session.getAttribute("username");
            if (username != null) {
            %>
            <p>Welcome, <%= username %>!</p>
            <button onclick="window.location.href='dashboardServlet'" class="btn">Go to Dashboard</button>
            <button onclick="window.location.href='logoutServlet'" class="btn">Logout</button>
            <%
            } else {
            %>
            <p>Welcome!</p>
            <button onclick="window.location.href='login'" class="btn">Login</button>
            <button onclick="window.location.href='Register'" class="btn">Sign Up</button>
            <%
            }
            %>
        </div>
    </div>
    <div class="gallery">
        <div class="carousel">
            <a class="carousel-item" href="#">
                <img src="images/cars-20250123T131644Z-001/cars/mclaren-1.jpg">
                <h4>765LT SPIDER</h4>
            </a>
            <a class="carousel-item" href="#">
                <img src="images/cars-20250123T131644Z-001/cars/mclaren-2.jpg">
                <h4 style="color: #92ff00">ARTURA</h4>
            </a>
            <a class="carousel-item" href="#">
                <img src="images/cars-20250123T131644Z-001/cars/mclaren-3.jpeg">
                <h4 style="color: #ff0015">7505</h4>
            </a>
            <a class="carousel-item" href="#">
                <img src="images/cars-20250123T131644Z-001/cars/mclaren-4.jpeg">
                <h4 style="color: #ff2c00">765LT</h4>
            </a>
            <a class="carousel-item" href="#">
                <img src="images/cars-20250123T131644Z-001/cars/mclaren-5.jpeg">
                <h4 style="color: #bbc0dd">7505 SPIDER</h4>
            </a>
        </div>
    </div>

</div>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script src="./assets/js/script.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $('.carousel').carousel({
            indicators: true
        });
        setInterval(function() {
            $('.carousel').carousel('next');
        }, 3000);
    });
    let currentVideoIndex = 0;
            const videos = document.querySelectorAll('.bg-video');

            function changeBackgroundVideo() {
                // Hide all videos
                videos.forEach((video, index) => {
                    video.classList.remove('active');
                    if (index === currentVideoIndex) {
                        video.pause(); // Pause the current video
                    }
                });

                // Increment the index for the next video
                currentVideoIndex = (currentVideoIndex + 1) % videos.length;

                // Show the next video
                videos[currentVideoIndex].classList.add('active');
                videos[currentVideoIndex].play(); // Play the next video
            }

            // Set an interval to change the video every 5 seconds
            setInterval(changeBackgroundVideo, 5000);
</script>
</body>
</html>
