# VK Video Patched

[![CI](https://github.com/Solvo37/VKlean/actions/workflows/ci.yml/badge.svg)](https://github.com/Solvo37/VKlean/actions/workflows/ci.yml)
[![Auto build](https://github.com/Solvo37/VKlean/actions/workflows/auto-update.yml/badge.svg)](https://github.com/Solvo37/VKlean/actions/workflows/auto-update.yml)
[![License: GPL-3.0](https://img.shields.io/badge/license-GPL--3.0-blue.svg)](./LICENSE)
[![Добавить в Obtainium](https://img.shields.io/badge/Obtainium-%D0%94%D0%BE%D0%B1%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-7c4dff?logo=android&logoColor=white)](https://apps.obtainium.imranr.dev/redirect?r=obtainium%3A%2F%2Fapp%2F%257B%2522id%2522%253A%2522com.vk.vkvideo%2522%252C%2522url%2522%253A%2522https%253A%252F%252Fgithub.com%252FSolvo37%252FVKlean%2522%252C%2522author%2522%253A%2522Solvo37%2522%252C%2522name%2522%253A%2522VK%2520Video%2520Patched%2522%252C%2522installedVersion%2522%253Anull%252C%2522latestVersion%2522%253A%25221.165.1%2522%252C%2522apkUrls%2522%253A%2522%255B%255B%255C%2522VK-Video-1.165.1-patched.apk%255C%2522%252C%255C%2522https%253A%252F%252Fgithub.com%252FSolvo37%252FVKlean%252Freleases%252Fdownload%252F1.165.1%252FVK-Video-1.165.1-patched.apk%255C%2522%255D%255D%2522%252C%2522preferredApkIndex%2522%253A0%252C%2522additionalSettings%2522%253A%2522%257B%255C%2522includePrereleases%255C%2522%253Afalse%252C%255C%2522fallbackToOlderReleases%255C%2522%253Afalse%252C%255C%2522filterReleaseTitlesByRegEx%255C%2522%253A%255C%2522%255C%2522%252C%255C%2522filterReleaseNotesByRegEx%255C%2522%253A%255C%2522%255C%2522%252C%255C%2522verifyLatestTag%255C%2522%253Atrue%252C%255C%2522sortMethodChoice%255C%2522%253A%255C%2522date%255C%2522%252C%255C%2522useLatestAssetDateAsReleaseDate%255C%2522%253Afalse%252C%255C%2522releaseTitleAsVersion%255C%2522%253Afalse%252C%255C%2522trackOnly%255C%2522%253Afalse%252C%255C%2522versionExtractionRegEx%255C%2522%253A%255C%2522%255C%2522%252C%255C%2522matchGroupToUse%255C%2522%253A%255C%2522%255C%2522%252C%255C%2522versionDetection%255C%2522%253Atrue%252C%255C%2522releaseDateAsVersion%255C%2522%253Afalse%252C%255C%2522useVersionCodeAsOSVersion%255C%2522%253Afalse%252C%255C%2522apkFilterRegEx%255C%2522%253A%255C%2522%255EVK-Video-.%252A-patched%255C%255C%255C%255C.apk%2524%255C%2522%252C%255C%2522invertAPKFilter%255C%2522%253Afalse%252C%255C%2522autoApkFilterByArch%255C%2522%253Afalse%252C%255C%2522appName%255C%2522%253A%255C%2522VK%2520Video%2520Patched%255C%2522%252C%255C%2522appAuthor%255C%2522%253A%255C%2522Solvo37%255C%2522%252C%255C%2522shizukuPretendToBeGooglePlay%255C%2522%253Afalse%252C%255C%2522allowInsecure%255C%2522%253Afalse%252C%255C%2522exemptFromBackgroundUpdates%255C%2522%253Afalse%252C%255C%2522skipUpdateNotifications%255C%2522%253Afalse%252C%255C%2522about%255C%2522%253A%255C%2522VK%2520Video%2520patched%2520builds%2520by%2520Solvo37%255C%2522%252C%255C%2522refreshBeforeDownload%255C%2522%253Atrue%257D%2522%252C%2522overrideSource%2522%253Anull%252C%2522allowIdChange%2522%253Afalse%252C%2522releaseUrl%2522%253A%2522https%253A%252F%252Fgithub.com%252FSolvo37%252FVKlean%252Freleases%252Ftag%252F1.165.1%2522%257D)

Патчи **Morphe** и готовая подписанная ARM64-сборка **VK Видео** без найденных рекламных блоков. Модифицированный VK Видео устанавливается рядом с обычным VK.

## Скачать

**Текущий релиз: 1.165.1**

Android внутри APK: **1.165 / versionCode 52788**.

➡️ [Скачать APK из последнего стабильного релиза](https://github.com/Solvo37/VKlean/releases/latest)

Файлы проверенного релиза `1.165.1`: [VK-Video-1.165.1-patched.apk](https://github.com/Solvo37/VKlean/releases/download/1.165.1/VK-Video-1.165.1-patched.apk) и [patches-1.165.1.mpp](https://github.com/Solvo37/VKlean/releases/download/1.165.1/patches-1.165.1.mpp).

В Releases публикуются **один готовый APK** для Obtainium и **один .mpp bundle** для Morphe. Служебные отчёты, checksums и build metadata остаются в GitHub Actions.

## Что изменено

- совместная установка с обычным `com.vkontakte.android`;
- bypass проверки подписи в `libvkcore.so`;
- отключение встроенного update prompt VK Видео;
- удаление найденных рекламных путей в обычном видео и VK Клипах;
- скрытие рекламных карточек/баннеров на Home, Discover и в профиле;
- блокировка ad pixel tracking;
- fail-closed проверки: если новая версия VK Видео несовместима с патчами, APK не публикуется.

Полный список активных патчей хранится в [patches-list.json](./patches-list.json).

Поддерживаемая тестовая база проекта: **Android 13 и новее**. Оставшиеся задачи опубликованы в [ROADMAP.md](./ROADMAP.md).

## Установка

1. Если установлен официальный **VK Видео**, удалите его один раз — официальный APK и этот проект подписаны разными сертификатами.
2. Обычный **VK** удалять не нужно.
3. Установите APK из [последнего стабильного релиза](https://github.com/Solvo37/VKlean/releases/latest).

Все релизы проекта подписываются одним постоянным сертификатом, поэтому следующие сборки ставятся поверх предыдущих.

## Автообновление

Workflow **VK Video auto build** каждые 6 часов проверяет RuStore и APKPure, валидирует package/certificate и выбирает самый новый подтверждённый `versionCode`.

Схема версий Releases отделена от Android `versionName`:

- текущий релиз: `1.165.1`;
- следующий rebuild этой же Android-версии: `1.165.2`;
- новая Android-версия начнёт собственную линию с ревизии `.0`.

Каждый Release immutable: существующий APK не перезаписывается. Это важно для корректной работы клиентов обновлений и кэша GitHub asset IDs.

### Obtainium

[![Добавить в Obtainium](https://img.shields.io/badge/Obtainium-%D0%94%D0%BE%D0%B1%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-7c4dff?logo=android&logoColor=white)](https://apps.obtainium.imranr.dev/redirect?r=obtainium%3A%2F%2Fapp%2F%257B%2522id%2522%253A%2522com.vk.vkvideo%2522%252C%2522url%2522%253A%2522https%253A%252F%252Fgithub.com%252FSolvo37%252FVKlean%2522%252C%2522author%2522%253A%2522Solvo37%2522%252C%2522name%2522%253A%2522VK%2520Video%2520Patched%2522%252C%2522installedVersion%2522%253Anull%252C%2522latestVersion%2522%253A%25221.165.1%2522%252C%2522apkUrls%2522%253A%2522%255B%255B%255C%2522VK-Video-1.165.1-patched.apk%255C%2522%252C%255C%2522https%253A%252F%252Fgithub.com%252FSolvo37%252FVKlean%252Freleases%252Fdownload%252F1.165.1%252FVK-Video-1.165.1-patched.apk%255C%2522%255D%255D%2522%252C%2522preferredApkIndex%2522%253A0%252C%2522additionalSettings%2522%253A%2522%257B%255C%2522includePrereleases%255C%2522%253Afalse%252C%255C%2522fallbackToOlderReleases%255C%2522%253Afalse%252C%255C%2522filterReleaseTitlesByRegEx%255C%2522%253A%255C%2522%255C%2522%252C%255C%2522filterReleaseNotesByRegEx%255C%2522%253A%255C%2522%255C%2522%252C%255C%2522verifyLatestTag%255C%2522%253Atrue%252C%255C%2522sortMethodChoice%255C%2522%253A%255C%2522date%255C%2522%252C%255C%2522useLatestAssetDateAsReleaseDate%255C%2522%253Afalse%252C%255C%2522releaseTitleAsVersion%255C%2522%253Afalse%252C%255C%2522trackOnly%255C%2522%253Afalse%252C%255C%2522versionExtractionRegEx%255C%2522%253A%255C%2522%255C%2522%252C%255C%2522matchGroupToUse%255C%2522%253A%255C%2522%255C%2522%252C%255C%2522versionDetection%255C%2522%253Atrue%252C%255C%2522releaseDateAsVersion%255C%2522%253Afalse%252C%255C%2522useVersionCodeAsOSVersion%255C%2522%253Afalse%252C%255C%2522apkFilterRegEx%255C%2522%253A%255C%2522%255EVK-Video-.%252A-patched%255C%255C%255C%255C.apk%2524%255C%2522%252C%255C%2522invertAPKFilter%255C%2522%253Afalse%252C%255C%2522autoApkFilterByArch%255C%2522%253Afalse%252C%255C%2522appName%255C%2522%253A%255C%2522VK%2520Video%2520Patched%255C%2522%252C%255C%2522appAuthor%255C%2522%253A%255C%2522Solvo37%255C%2522%252C%255C%2522shizukuPretendToBeGooglePlay%255C%2522%253Afalse%252C%255C%2522allowInsecure%255C%2522%253Afalse%252C%255C%2522exemptFromBackgroundUpdates%255C%2522%253Afalse%252C%255C%2522skipUpdateNotifications%255C%2522%253Afalse%252C%255C%2522about%255C%2522%253A%255C%2522VK%2520Video%2520patched%2520builds%2520by%2520Solvo37%255C%2522%252C%255C%2522refreshBeforeDownload%255C%2522%253Atrue%257D%2522%252C%2522overrideSource%2522%253Anull%252C%2522allowIdChange%2522%253Afalse%252C%2522releaseUrl%2522%253A%2522https%253A%252F%252Fgithub.com%252FSolvo37%252FVKlean%252Freleases%252Ftag%252F1.165.1%2522%257D)

Обе кнопки импортируют APK стабильного релиза `1.165.1` и источник GitHub Releases. Перед скачиванием Obtainium обновляет сведения о релизе (`refreshBeforeDownload`); возврат к старым релизам отключён (`fallbackToOlderReleases`). Фильтр выбирает только `VK-Video-*-patched.apk`, исключая Morphe bundle.

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

### Уже настроенные источники

После переименования репозитория старые GitHub URL `Solvo37/VK-Video-Morphe-Patches` (в том числе в нижнем регистре) перенаправляются в `Solvo37/VKlean`. Проверены страницы Releases, GitHub API, raw-манифест и ссылки на APK/bundle релиза `1.165.1`. Существующие источники Obtainium и Morphe можно оставить; для новых установок используйте `Solvo37/VKlean`. Package ID `com.vk.vkvideo`, имена файлов и ключ подписи не меняются.

## Morphe на Android

Тот же репозиторий можно добавить в **Morphe** как удалённый источник патчей:

```text
https://github.com/Solvo37/VKlean
```

Или открыть готовую ссылку на Android:

https://morphe.software/add-source?github=Solvo37/VKlean

После каждого успешного compatibility build GitHub Actions публикует `.mpp`, обновляет `patches-bundle.json` и помечает проверенную версию VK Видео как стабильную цель. Morphe затем может обновлять источник прямо с GitHub и патчить оригинальный APK локально на устройстве.

Это отдельный способ установки от Obtainium: Obtainium получает уже готовый подписанный APK, а Morphe применяет тот же набор патчей самостоятельно на Android.

## Для разработки

Сборка Morphe bundle:

```bash
gradle :patches:buildAndroid
```

Ключевые файлы:

- `patches/` — исходники патчей;
- `ci/` — проверки совместимости и выбор upstream;
- `.github/workflows/auto-update.yml` — автоматическая сборка и публикация;
- `CHANGELOG.md` — история изменений.

## Важно

Проект не связан с VK, VK Видео, Morphe или Obtainium и не одобрен ими. Репозиторий не содержит исходный код VK Видео.

Код проекта: [GPL-3.0](./LICENSE).
