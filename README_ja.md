# Gaming Everything

Gaming Everythingは、ブロック、空、エンティティ、アイテム、一人称視点の手、パーティクル、HUD、メニューなどを、明るく流れるRGBレインボー表示にするMinecraft用クライアントMODです。

サーバーへの導入は不要です。ゲーム内で**Gキー**を押すか、**`/gaming`**を実行すると設定画面を開けます。

## 対応バージョン

| Minecraft | Fabric | Forge | NeoForge |
| --- | --- | --- | --- |
| 1.20.1 | 対応 | 対応 | ― |
| 1.21.1 | 対応 | 対応 | 対応 |
| 26.2 | 対応 | ― | 対応 |

Minecraftのバージョンとローダーの両方が一致するjarを使用してください。

## 主な機能

- 地形やブロックをワールド座標基準のアニメーションレインボーで表示
- 空、生物、プレイヤー、アイテム、一人称視点の手をRGB化
- パーティクル、HUD枠、クロスヘア、GUI、文字にもRGB効果を適用
- 主な表示カテゴリごとに変化速度と波長を調整可能
- 色の強さと各効果を個別に設定可能
- **Gキー**またはクライアントコマンドからゲーム内設定が可能
- 設定は `config/gamingeverything.json` に保存

## コマンド

すべてクライアント側のコマンドなので、OP権限は不要です。

| コマンド | 動作 |
| --- | --- |
| `/gaming` | 設定画面を開く |
| `/gaming on` | MOD全体を有効化 |
| `/gaming off` | MOD全体を無効化 |
| `/gaming toggle` | MOD全体の有効・無効を切り替える |
| `/gaming <category>` | 指定カテゴリの有効・無効を切り替える |
| `/gaming <category> on` | 指定カテゴリを有効化 |
| `/gaming <category> off` | 指定カテゴリを無効化 |
| `/gaming <category> toggle` | 指定カテゴリの有効・無効を切り替える |

利用可能なカテゴリは `master`、`blocks`、`sky`、`entities`、`hands`、`items`、`gui`、`particles`、`hud`、`special` です。

実行例：

```text
/gaming blocks off
/gaming sky toggle
/gaming gui on
```

## 導入方法

1. 対象Minecraft版のFabric、Forge、またはNeoForgeを導入します。
2. 配布ページやランチャーに表示される依存MODがあれば導入します。
3. Minecraft版とローダーが一致するjarを、対象インスタンスの `mods` フォルダに入れます。
4. Minecraftを起動し、**Gキー**で表示を調整します。

## ビルド

Windowsでは `gradlew.bat prepareRelease`、Linux/macOSでは `./gradlew prepareRelease` を実行します。配布用jarとSHA-256ファイルは `release-artifacts` にまとめられます。
