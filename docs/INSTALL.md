# Установка

## Скачать

Актуальный APK всегда находится в [Latest Release](https://github.com/Solvo37/VKlean/releases/latest).

Проверенный стабильный релиз: **1.165.1**, Android **1.165 / versionCode 52788**. При установке выбирайте APK из Latest Release; имя файла ниже относится к этому проверенному релизу:

```text
VK-Video-1.165.1-patched.apk
```

В каждом публичном Release намеренно находится только **один APK**.

## Первая установка

Проект подписывает APK собственным постоянным Android-ключом, поэтому первую проектную сборку нельзя установить поверх официального **VK Видео**.

1. Удалите только официальный **VK Видео** (`com.vk.vkvideo`).
2. Обычный **VK** (`com.vkontakte.android`) оставьте установленным.
3. Установите APK из Latest Release.
4. Разрешите установку из выбранного браузера/файлового менеджера, если Android попросит.

Следующие сборки проекта подписываются тем же ключом и устанавливаются поверх предыдущей проектной версии.

## Obtainium

Repository URL:

```text
https://github.com/Solvo37/VKlean
```

APK asset filter:

```text
^VK-Video-.*-patched\.apk$
```

Release title filter при необходимости:

```text
^VK Video
```

В Obtainium включите обновление сведений перед скачиванием (`refreshBeforeDownload`) и отключите возврат к старым релизам (`fallbackToOlderReleases`). Готовые кнопки импорта находятся в [README](../README.md#obtainium).

Старый URL `Solvo37/VK-Video-Morphe-Patches` перенаправляется в `Solvo37/VKlean`; существующий источник можно оставить без переустановки приложения.

## Проверка подписи

Ожидаемый сертификат проектных APK:

```text
D4:1F:49:2F:0E:2A:2E:39:90:AC:7F:8E:75:CC:5D:4B:
14:89:5F:7B:46:C0:B6:11:3B:78:82:C4:8A:A5:D4:0A
```

Проверка:

```bash
apksigner verify --verbose --print-certs VK-Video-*-patched.apk
```

Pipeline использует `zipalign` и APK Signature Scheme **v3 only**.
