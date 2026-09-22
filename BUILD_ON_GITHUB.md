# Compilar o APK gratuitamente pelo GitHub

Este projeto foi preparado para usar **GitHub Actions com um runner padrão**. Em um repositório público, os runners padrão hospedados pelo GitHub são gratuitos e sem limite de minutos.

## Passo a passo

1. Crie uma conta gratuita em https://github.com/ se ainda não tiver.
2. Crie um **novo repositório público**.
3. Envie **todos os arquivos desta pasta** para a raiz do repositório.
4. No GitHub, abra a aba **Actions**.
5. Se aparecer a opção para habilitar workflows, habilite-a.
6. No menu lateral, selecione **Compilar APK grátis**.
7. Clique em **Run workflow**.
8. Aguarde a compilação terminar.
9. Abra a execução concluída.
10. Na seção **Artifacts**, baixe **Igor-Offline-RPG-debug**.
11. Dentro do ZIP estará o arquivo `app-debug.apk`.

## Importante para não pagar

- O repositório deve ser **público** para usar o runner padrão gratuitamente e sem limite de minutos.
- O workflow usa somente `ubuntu-latest`, que é um runner padrão.
- Não altere `runs-on` para um runner maior/premium.
- Este workflow não exige Codemagic.
- Não é necessário cartão de crédito para esta forma de compilação.

Se você preferir manter o repositório privado, o GitHub Free inclui atualmente 2.000 minutos/mês para Actions; depois da cota, o uso adicional pode ser cobrado dependendo das configurações da conta. Para manter o objetivo de custo zero, use um repositório público.

## O que o workflow faz

1. Baixa o projeto.
2. Instala Java 17.
3. Configura o Android SDK.
4. Instala Android API 35 e Build Tools 35.0.0.
5. Configura Gradle 8.9.
6. Executa `gradle assembleDebug`.
7. Entrega o APK como Artifact.

Não é necessário instalar Android Studio no celular ou no computador para fazer essa compilação pelo GitHub.
