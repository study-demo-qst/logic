# templateプロジェクトの使い方
1. Webからリポジトリ新規作成。templateを指定
1. pom.xmlのmaven_logicを変更（2か所）
1. pom.xmlのmaven_commonを変更（2か所）
1. settingsは引き継がれない
    1. Personal Access Token作成。read:packagesだけでOK
1. リポジトリ作成と同時にactionsが実行され、NGになっている。再実行

# templateプロジェクトの作り方
1. VSCからSpring Initializerで作成
1. setting.xmlを追加、pom.xmlにrepositoryを追加、workflow fileを追加
    1. commonのpackagesを取得
1. webから以下を設定
    1. Personal Access Token作成。read:packagesだけでOK
    1. このリポジトリにPATを追加。有効期限があるので注意

# Github Appsの作成
1. organizationアカウントに切り替え
1. settings-> Developper settings  -> Gihtub Apps -> New Github App
    1. URLは適当でよい
    1. WebhookのActiveはOFF
    1. Repository PermissionのPakcagesにread-onlyを付ける（Metadataも自動で付く）
    1. 作成。
        1. ページ末尾の「Private keys」を押下（ページ途中にもあるので注意）
        1. pemファイルがDLされる

# Github Appsのインストール
1. organizationのsettings -> Github Apps -> Appのアイコンをクリック
1. 左メニューのInstall App -> organizationを選択
1. リポジトリのsettings -> secrets and variables -> Secrets追加
    1. APP_ID=378486　※workflowの記述と合わせる
    1. PRIVATE_KEY=pemの中身　※workflowの記述と合わせる
