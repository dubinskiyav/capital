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


Комментарии по Spring:

Точка входа CapitalApplication - он запускается

InitApp запускается первым всегда при запуске

В пакете model - модели таблиц, например,
@Table(name = "measure")
public class Measure {

В пакете repository - репозитории таблиц, отнаследованные от TableRepository
В начальном случае они пустые, так как TableRepository сделаны дефаулт методы для
основных операций типа count, insert, update, delete, deleteAll, insertOrUpdate, set
но можно не наследовать, а писать свои инсерты или наследовать но перегружать
TableRepository все берет из аннотаций @Table @Column и тп
Я добавил в репозитории создане таблиц и первоначальную загрузку данных, не уверен, что так надо

В пакете controllers - контроллеры
например, MeasureController
метод
@RequestMapping(value = "json", method = RequestMethod.POST)
public List<Measure> measure(
@RequestBody GridDataOption gridDataOption
) {
именно он вызывается в reactе
reqwest({
url: URI_SELECT,
contentType: "application/json; charset=utf-8",
method: 'post',
type: 'json',
data:JSON.stringify(gridDataOption)
})
ну и так далее
там сейчас сделаны вызовы дефайлтных методов репозиторием, типа
measureRepository.findAll
но можно свои писать
в
@RequestMapping(value = "dto", method = RequestMethod.POST)
public List<UnitmeasureDTO> unitmeasureDTO(
@RequestBody GridDataOption gridDataOption
) {
написан свой, прямо SELECT
и возвращает не модель а DTO, которые в пакете dto
сделан один DTO для примера

В пакете validators - валидаторы
именно они добавляют errorCode в ответ
но это происходит и без валидаторв, например, при удалении
dataBinder.getBindingResult().rejectValue("id", "", e.getCause().getMessage());
throw new PostRecordException(dataBinder.getBindingResult(), e);

В Config - конфигурация приложения
сейчас там добавлены разрешения для корса

В пакете exceptions - исключения
сделаны зачатки ради изучения

В пакете utils - утилиты

Настройка на базу данных и прочее - в файле application.properties


Есть интеграционные тесты
у них свой application.properties
в которых указана своя тестовая база данных

Точка входа в тесты - CapitalApplicationTests
там сделан метод с аннотацией @BeforeEach - он вызывается перед тестами
неапример пересоздать базу и загрузить данные

сами тесты в пакете controllers
там тоже можно делать @BeforeEach - будет вызываться каждый раз при запуске теста из этого контроллера
не использовать

С тестами непросто. Я, например, не вижу особой в них необходимости
Они запускаются при каждой сборке проекта

Сборка проекта - пакетный файл
make_package.cmd
создает файл capital.jar который и запусается на сервере
