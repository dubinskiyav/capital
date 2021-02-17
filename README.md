1. Устанавливаем IntelliJ IDEA Community
   https://www.jetbrains.com/help/idea/installation-guide.html#standalone
   Скачать
   уустановить
   sudo tar -xzf ideaIC-2020.3.2.tar.gz -C /opt
   Запустить /opt/idea12345/bin/idea.sh
   установить  плагин SonarLint

2 File -> New -> Project… Spring Initializr
   но так получится только если у вас Ultimate за тысячу баксов в год

3 Создаем пустой проект Spring
    https://start.spring.io/
    Maven Java 2.4.2
    Group biz.gelicon
    Artifact    my-test-project
    Name    my-test-project
    Descritor  Мой тетстовый  проект
    Package name  biz.gelicon.my-test-project
    Jar
    11
    Жмем Generate
    Полученный  файл  тащим в  папку разработки и распакковываем и  открываем  в IntelliJ IDEA

4. Пробуем
   https://javarush.ru/groups/posts/2537-chastjh-8-pishem-neboljhshoe-prilozhenie-na-spring-boot

5. Но лучше скачать
   git clone https://github.com/dubinskiyav/capital.git
   и открыть его
   Ждем пока все вкачается
   
6. Build - Build Project
   должно так
   Build completed successfully in 20 sec, 736 ms
   
7. Add Configuration
    +   Application
        name - CapitalApplication
        Java 11
        Main Class - biz.gelicon.capital.CapitalApplication
        Ok
        Run
        Должно получится так   
        SpringApplication.run...Ok
        
8. Запускаем Visual Studio Code
    Open Folder - capital-react - Ok
    Открываем файл const.js и меняем удаленный сервер на локальный
    export const startURL = "http://localhost:8080/";
    //export const startURL = "http://78.40.219.225:8080/";
    Сохраняем файл!
   
9. Идем в папку приложения React и запускаем
    npm start
    Когда запустится - нажать на Единицы измерения -
    в консоли IntelliJ IDEA должны побежать строчки и в таблице должны появится данные
    Это означает, что приложение react подцепилось к локально запущенному серверу