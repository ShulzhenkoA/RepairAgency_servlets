<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<h3>Вибравши RCA ви отримуєте:</h3>
<div class="container" id="aboutUs">
    <div class="col-md-10 offset-md-1 rounded">
        <div class="row">
            <div class="col-md-6"><span class="badge badge-pill badge-dark">1</span> Виконання послуги
                кваліфікованим персоналом
            </div>
            <div class="col-md-6"><span class="badge badge-pill badge-dark">6</span> Проведення
                комплексного ремонту вашого авто
            </div>
        </div>
        <div class="row">
            <div class="col-md-6"><span class="badge badge-pill badge-dark">2</span> Гарантію вирішення
                проблеми або повернення коштів
            </div>
            <div class="col-md-6"><span class="badge badge-pill badge-dark">7</span> Безкоштовну
                консультацію та попередню діагностику
            </div>
        </div>
        <div class="row">
            <div class="col-md-6"><span class="badge badge-pill badge-dark">3</span> Можливість
                контролювати
                виконання ремонту
            </div>
            <div class="col-md-6"><span class="badge badge-pill badge-dark">8</span> Максимальну
                швидкість
                обслуговування
            </div>
        </div>
        <div class="row">
            <div class="col-md-6"><span class="badge badge-pill badge-dark">4</span> Відсутність
                будь-яких
                передплат
            </div>
            <div class="col-md-6"><span class="badge badge-pill badge-dark">9</span> Гарантію на
                результати
                проведеного ремонту
            </div>
        </div>
        <div class="row">
            <div class="col-md-6"><span class="badge badge-pill badge-dark">5</span> Проведення
                локального
                ремонту окремих частин
            </div>
            <div class="col-md-6"><span class="badge badge-pill badge-dark">10</span> Замовлення ремонту
                на
                зручний для Вас час
            </div>
        </div>
    </div>
</div>

<div class="container" id="suggestions">
    <h4> МИ ПРОВОДИМО:</h4>
    <div class="row col-md-12">
        <div class="col-md-4">
            <h5>Ремонт двигунів</h5>
            <div class="movableIcon">
                <img src="static/img/engine.png" alt="chassis picture">
            </div>
        </div>
        <div class="col-md-4">
            <h5>Ремонт шасі</h5>
            <div class="movableIcon">
                <img src="static/img/chassis.png" alt="engine pictre">
            </div>
        </div>
        <div class="col-md-4">
            <h5>Ремонт коробки передач</h5>
            <div class="movableIcon">
                <img src="static/img/gearshift.png" alt="suspension picture">
            </div>
        </div>
    </div>
    <div class="row col-md-12">
        <div class="col-md-4">
            <h5>Заміну акамулятора</h5>
            <div class="movableIcon">
                <img src="static/img/accumulator.png" alt="chassis picture">
            </div>
        </div>
        <div class="col-md-4">
            <h5>Заміну масла</h5>
            <div class="movableIcon">
                <img src="static/img/oil.png" alt="engine pictre">
            </div>
        </div>
        <div class="col-md-4">
            <h5>Фарбувальні роботи</h5>
            <div class="movableIcon">
                <img src="static/img/car-painting.png" alt="suspension picture">
            </div>
        </div>
    </div>
</div>

<div class="row col-md-12" id="reviewsCards">
    <div class="col-md-12" id="reviewsHeader">
        <h2>Останні відгуки про нас</h2>
    </div>
    <div class="row col-md-12">
        <div class="card col-md-3">
            <div class="card-body">
                <h5>Юрій</h5>
                <p>Some example text some example text. John Doe is an architect and engineer</p>
                <p class="reviewsDate"><small>06.02.2016 11:02</small></p>
            </div>
        </div>
        <div class="card col-md-3">
            <div class="card-body">
                <h5>Катерина</h5>
                <p>Some example text some example text. John Doe is an architect and engineer</p>
                <p class="reviewsDate"><small>06.02.2016 11:02</small></p>
            </div>
        </div>

        <div class="card col-md-3">
            <div class="card-body">
                <h5>Євгеній</h5>
                <p>Some example text some example text. John Doe is an architect and engineer</p>
                <p class="reviewsDate"><small>06.02.2016 11:02</small></p>
            </div>
        </div>
        <div class="card col-md-3">
            <div class="card-body">
                <h5>Антон</h5>
                <p>Some example text some example text. John Doe is an architect and engineer</p>
                <p class="reviewsDate"><small>06.02.2016 11:02</small></p>
            </div>
        </div>
    </div>
    <div class="row col-md-12" id="reviewsLink">
        <div class="col-md-5 offset-md-7">
            <a href="${pageContext.request.contextPath}/reviews">Переглянути всі відгуки або залишти свій</a>
        </div>
    </div>
</div>

<div class="row" id="contacts">
    <div class="col-md-12">
        <h4> КОНТАКТИ :</h4>
        <hr>
    </div>
    <div class="col-md-6">
        <p>Графік роботи автосервісів</p>
        <p>Пн-Пт: 09:00-18:00</p>
        <p>Сб: 10:00-15:00</p>
    </div>
    <div class="col-md-6">
        <p>Графік роботи колл-центру</p>
        <p>Пн-Пт: 08:00-21:00</p>
        <p>Сб-Нд: 10:00-20:00</p>
    </div>
    <div class="col-md-4 offset-md-4">
        <hr>
        <h5><i class="fas fa-phone-alt"></i> (044) 333-44-55 </h5>
        <h5><i class="fas fa-envelope"></i>rca.official@gmail.com</h5>
        <hr>
        <h4>Адреса наших автосервісів</h4>
        <p><i class="fas fa-map-marker-alt"></i>Київ, вул .Пражська, 15</p>
        <br>
        <p><i class="fas fa-map-marker-alt"></i>Чернігів, вул. Рокосовського, 38</p>
        <br>
        <p><i class="fas fa-map-marker-alt"></i>Полтава, вул. Пушкіна, 112</p>
    </div>
</div>