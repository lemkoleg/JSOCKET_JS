/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib_exceptions

import kotlin.js.JsName


/**
 *
 * @author Oleg
 */

private val Exceptions: Map<Int, exception_class> =
    mapOf(
        80011 to exception_class(
            "Error opening file. Try again",
            "Ошибка открытия файла. Попробуйте еще.",
            "Помилка відкриття файла. Спробуйте ще."
        ),
        80013 to exception_class(
            "Error to send file. Try again",
            "Ошибка отправки файла. Попробуйте еще.",
            "Помилка відправки файла. Спробуйте ще."
        ),
        80015 to exception_class(
            "Error to open channel for send file. Try again",
            "Ошибка открытия канала для отправки файла. Попробуйте еще.",
            "Помилка відкриття каналу для відправки файла. Спробуйте ще."
        ),
        80021 to exception_class(
            "Error to write the file. Try again",
            "Ошибка записи файла. Попробуйте еще.",
            "Помилка запису файла. Спробуйте ще."
        ),
        90001 to exception_class(
            "Uncertain error",
            "Неопределенная ошибка",
            "Невизначена помилка"
        ),
        90002 to exception_class(
            "There is no way to send data to the server",
            "Нет возможности отослать данные серверу",
            "Нема можливості надіслати дані серверу"
        ),
        90003 to exception_class(
            "Can not connect to server",
            "Невозможно установить связь с сервером",
            "Неможиво зв'язатися з сервером"
        ),
        90004 to exception_class(
            "The server did not respond to your request",
            "Сервер не ответил на Ваш запрос",
            "Сервер не відповів на Ваш запит"
        ),
        90005 to exception_class(
            "Connection id is empty",
            "Идентификатор подключения пустой",
            "Ідентифікатор підключення пустий"
        ),
        90006 to exception_class(
            "Invalid file size",
            "Неверный размер файла",
            "Невірний розмір файлу"
        ),
        90007 to exception_class(
            "Invalid file extension",
            "Неверное разширение файла",
            "Невірне розширення файлу"
        ),
        90008 to exception_class(
            "File storage location not specified",
            "Не указано место хранения файла",
            "Не вказано місцезнаходження файлу"
        ),
        90009 to exception_class(
            "ID # 1 field is empty",
            "Не заполнено поле идентификатора №1",
            "Не заповнено поле ідентифікатора №1"
        ),
        90010 to exception_class(
            "ID # 2 field is empty",
            "Не заполнено поле идентификатора №2",
            "Не заповнено поле ідентифікатора №2"
        ),
        90011 to exception_class(
            "ID # 3 field is empty",
            "Не заполнено поле идентификатора №3",
            "Не заповнено поле ідентифікатора №3"
        ),
        90012 to exception_class(
            "ID # 4 field is empty",
            "Не заполнено поле идентификатора №4",
            "Не заповнено поле ідентифікатора №4"
        ),
        90013 to exception_class(
            "ID # 5 field is empty",
            "Не заполнено поле идентификатора №5",
            "Не заповнено поле ідентифікатора №5"
        ),
        90014 to exception_class(
            "Value # 1 field is empty",
            "Не заполнено поле значения №1",
            "Не заповнено поле значення №1"
        ),
        90015 to exception_class(
            "Value # 2 field is empty",
            "Не заполнено поле значения №2",
            "Не заповнено поле значення №2"
        ),
        90016 to exception_class(
            "Value # 3 field is empty",
            "Не заполнено поле значения №3",
            "Не заповнено поле значення №3"
        ),
        90017 to exception_class(
            "Value # 4 field is empty",
            "Не заполнено поле значения №4",
            "Не заповнено поле значення №4"
        ),
        90018 to exception_class(
            "Value # 5 field is empty",
            "Не заполнено поле значения №5",
            "Не заповнено поле значення №5"
        ),
        90019 to exception_class(
            "Value # 6 field is empty",
            "Не заполнено поле значения №6",
            "Не заповнено поле значення №6"
        ),
        90020 to exception_class(
            "Value # 7 field is empty",
            "Не заполнено поле значения №7",
            "Не заповнено поле значення №7"
        ),
        90021 to exception_class(
            "Value # 8 field is empty",
            "Не заполнено поле значения №8",
            "Не заповнено поле значення №8"
        ),
        90022 to exception_class(
            "Value # 9 field is empty",
            "Не заполнено поле значения №9",
            "Не заповнено поле значення №9"
        ),
        90023 to exception_class(
            "No date of last sync",
            "Не указана дата последней синхронизации",
            "Не вказано дати останньої синхронізації"
        ),
        90024 to exception_class(
            "No content found",
            "Не обнаружено контента",
            "Не знайдено контенту"
        ),
        90025 to exception_class(
            "MAC Adress is empty",
            "Пустой MAC-адресс",
            "Пуста MAC-адреса"
        ),
        90026 to exception_class(
            "Can't initalised app",
            "Невозможно инициализировать приложение",
            "Неможливо ініціліазувати додаток"
        ),
        90027 to exception_class(
            "This function is only available for the PRO version ",
            "Даная функция доступна только для PRO-версии",
            "Дана функція доступна лише для PRO-версії"
        ),
        90028 to exception_class(
            "For further use of the program, confirm your E-MAIL",
            "Для дальнейшего использования программы, подвердите Ваш E-MAIL",
            "Для подальшого використання програми, підвердить Ваш E-MAIL"
        ),
        90029 to exception_class(
            "No answer from file manager",
            "Нет ответа от файл менеджера",
            "Нема відповіді від файл менеджера"
        ),
        90030 to exception_class(
            "No connection",
            "Нет подключения",
            "Нема підключення"
        ),
        90031 to exception_class(
            "You are not logged in",
            "Вы не ввошли в приложение",
            "Ви не ввійшли до застосунку"
        ),
        90032 to exception_class(
            "Command not found",
            "Команда не найдена",
            "Команда не знайдена"
        ),
        90033 to exception_class(
            "Wrong commands format",
            "Неверный формат команды",
            "Невірний формат команди"
        ),
        90034 to exception_class(
            "Error sending file",
            "Ошибка при отправке файла",
            "Помилка відпраки файлу"
        ),
        90035 to exception_class(
            "Email-address cannot be empty",
            "Email-аддрес не может быть пустым",
            "Email-адреса не може бути пустою"
        ),
        90036 to exception_class(
            "Password cannot be empty",
            "Пароль не может быть пустым",
            "Пароль не може бути пустим"
        ),
        90037 to exception_class(
            "Verification code cannot be empty",
            "Проверочный код не может быть пустым",
            "Перевірочний код не може бути пустим"
        )
    )

@JsName("return_exceptions_text")
fun return_exceptions_text(num_of_exc: Int, lang: String): String? =
    (Exceptions[num_of_exc] ?: error("")).return_exceptions_text(lang)