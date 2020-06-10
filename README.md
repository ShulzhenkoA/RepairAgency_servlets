# RepairAgency_servlets

__System Repair Agency (Car Repair Agency)__. The user can register, create order for
car repair, leave review on the work performed. The manager can accept the order, specify the price,
reject the order, comment the order, view all active orders or order history, view list of masters or customers.
The master can execute (edit status) the order accepted by the manager, view his active orders or
history of completed orders. An administrator can create, modify or delete master or manager accounts.
___
### You can view the system on the AWS server by following link:
##### http://carrepairagency.eu-central-1.elasticbeanstalk.com/home
___
### Instructions for installing and running on a local server (Tomcat):

1. Install the JDK at least version 8. Set environment variables for Java.  
2. Install Maven at least version 3. Set environment variables for Maven.  
3. Install MySQl at least version 8 and create a __'root'__ user with the password: __123456789__.  
4. Install Tomcat at least version 9. Open the __tomcat-users.xml__ file in the _conf_ folder (_/Tomcat 9.0/conf/tomcat-users.xml_).
In __tomcat-users.xml__ file after the tag __<tomcat-users ...>__ insert the tag: __<user username = "user" password = "pass" roles = "manager-gui, manager-script"/>__
or replace with the specified tag if __<user ...>__ already exists, save the changes. Download __mysql-connector-java-8.0.20.jar__
(_https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.20_) and place it in the _lib_ folder (_/Tomcat 9.0/lib_).
Run Tomcat (in the _bin_ folder (_/Tomcat 9.0/bin/startup.bat_) run the __startup.bat__ file or in the __Tomcat Monitor__ run
service (__Start Service__)).
5. Download the project (clone via git (_https://github.com/ShulzhenkoA/RepairAgency_servlets.git_) or download
project and unzip).  
6. Create a database and populate it by running two SQL scripts in the project folder
(/RepairAgency_servlets/src/main/resources/database/__CreateRepairAgencyDatabase.sql__ and
/RepairAgency_servlets/src/main/resources/database/__PopulateDB.sql__).
7. Open the _Command Prompt_ (console) from the project folder (_RepairAgency_servlets_) or navigate in the _Command Prompt_ 
to the project folder (_RepairAgency_servlets_) where contains __pom.xml__.
8. Execute in the _Command Prompt_ the command: __mvn tomcat7:deploy__
9. When the deployment is complete, go in your browser to __http://localhost:8080/CarRepairAgency/home__.
10. If the population of the database has been executed (item 6), then the system already has:
    > Administrator - email: __admin@mail.com__, password: __Admin123__;  
    Customer - email: __customer@mail.com__, password: __Customer123__;  
    Master - email: __master@mail.com__, password: __Master123__;  
    Manager - email: __manager@mail.com__, password: __Manager123__.
11. To shut down the system and Tomcat server run the __shutdown.bat__ file in the _bin_ folder (/Tomcat 9.0/bin/shutdown.bat) 
or stop the service in __Monitor Tomcat__ (__Stop Service__).
___
___
___
__Система Ремонтне Агенство(Агенство з Ремонту Автомобілів)__. Користувач може зареєструватися, створити заявку на
ремонт автомобіля, залишити відгук про виконані роботи. Менеджер може прийняти заявку, вказавши ціну, або
відхилити заявку, вказавши причину, переглянути активні замовлення, історію замовлень, список майстрів або клієнтів.
Майстер може виконати (редагувати статус) прийняту менеджером заявку, переглянути свої активні замовлення або 
історію виконаних замовлень. Адміністратор може створити облікові записи майстра або менеджера, змінити їх або видалити.
___
### Переглянути систему на сервері AWS можна за посиланням:
##### http://carrepairagency.eu-central-1.elasticbeanstalk.com/home
___
### Інструкція з встановлення та запуску на локальному сервері (Tomcat):

1. Встановіть JDK не нижче 8 версії. Встановіть змінні середовища для Java.
2. Встановіть Maven не нижче 3 версії. Встановіть змінні середовища для Maven.
3. Встановіть MySQl не нижче 8 версії та створіть користувача __root__ з паролем: __123456789__.
4. Встановіть Tomcat не нижче 9 версії. В папці _conf_ (_/Tomcat 9.0/conf/tomcat-users.xml_) відкрийте файл __tomcat-users.xml__,
в якому після тегу __<tomcat-users...>__ вставте тег: __<user username="user" password="pass" roles="manager-gui, manager-script"/>__ 
або замініть на вказаний тег якщо __<user...>__ вже існує, збережіть зміни. Завантажте __mysql-connector-java-8.0.20.jar__ 
(_https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.20_) та помістіть його в папку _lib_ (_/Tomcat 9.0/lib_). 
Запустіть Tomcat (в папці _bin_ (_/Tomcat 9.0/bin/startup.bat_) запустіть файл __startup.bat__ або в __Monitor Tomcat__ запустіть 
службу (__Start Service__)).
5. Завантажте проект (зклонуйте через git (_https://github.com/ShulzhenkoA/RepairAgency_servlets_) або завантажте 
проект та розархівуйте).
6. Створіть базу даних та наповніть її, виконавши два SQL скріпти, що знаходяться в папці проекту 
(/RepairAgency_servlets/src/main/resources/database/__CreateRepairAgencyDatabase.sql__ та 
/RepairAgency_servlets/src/main/resources/database/__PopulateDB.sql__).
7. Відкрийте _Командний рядок_ (консоль) з папки проекту (_RepairAgency_servlets_) або перейдіть у _Командному рядку_ 
до папки проету (_/RepairAgency_servlets_), де міститься __pom.xml__.
8. Виконайте в _Командному рядку_ команду: __mvn tomcat7:deploy__
9. Після завершення розгорнення перейдіть в браузері за посиланням __http://localhost:8080/CarRepairAgency/home__.
10. Якщо виконано наповнення бази даних (пункт 6), то в системі вже існують:  
    >Адміністратор - email: __admin@mail.com__, пароль: __Admin123__;  
    Клієнт - email: __customer@mail.com__, пароль: __Customer123__;  
    Майстер - email: __master@mail.com__, пароль: __Master123__;  
    Менеджер - email: __manager@mail.com__, пароль: __Manager123__.  
11. Щоб завершити роботу системи та сервера Tomcat виконайте в папці _bin_ (_/Tomcat 9.0/bin/shutdown.bat_) файл __shutdown.bat__
або в __Monitor Tomcat__ зупиніть службу (__Stop Service__).