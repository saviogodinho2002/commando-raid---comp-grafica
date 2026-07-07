# Commando Raid — Clone em Java

Projeto de Computação Gráfica que recria, em 2D, a dinâmica de **Commando Raid**: controle de um canhão terrestre que abate soldados lançados de aviões. Tudo foi implementado em Java/Swing, sem bibliotecas externas.

---

## 🕹️ Jogabilidade & Controles

| Ação                         | Tecla / Botão                    |
| ---------------------------- | -------------------------------- |
| Mover canhão à esquerda      | **A**                            |
| Mover canhão à direita       | **D**                            |
| Mirar                        | **Posição do mouse**             |
| Disparar projétil (bala)     | **Clique esquerdo do mouse**     |
| Lançar bomba (recarga \~2 s) | **Qualquer clique não-esquerdo** |

* A pontuação máxima persiste em `res/highscore.txt`.

---

## ✨ Funcionalidades

* **Loop de jogo** com *tick* lógico separado da renderização.
* **Double-buffering** (`BufferStrategy`) para renderização suave.
* **Sprite-sheet** único (`res/spritesheet.png`) com todas as texturas.
* **Efeitos de explosão** (fade-out por alpha-blending).
* **Som** para tiros e explosões com reuso de `Clip`.
* **TileMap** simples que repete blocos para formar o chão.
* **Persistência** automática de high-score.
* **Menu inicial** (`StartGameController`) com botões *Iniciar* / *Sair*.

---

## ⚙️ Requisitos

* **JDK 11 ou superior**
  (usa `jdk.jfr.Unsigned`, presente a partir do JDK 11).
* Nenhuma dependência externa além da biblioteca padrão Java.

---

## 🗂️ Estrutura do Projeto

```
commando-raid/
├── src/
│   ├── Main.java              # Entry-point (cria JFrame e thread do Game)
│   ├── Game.java              # Loop, render, input
│   ├── Player.java            # Movimento, mira, bomba
│   ├── Bullet.java / Bomb.java
│   ├── Enemy.java / AirPlane.java
│   ├── Explosion.java
│   ├── Tile.java / TileMap.java
│   ├── SpriteSheet.java       # Carrega sprites e helpers de cor/alpha
│   ├── Sound.java             # Carrega/gerencia áudio
│   └── StartGameController.java
├── res/                       # Sprites, sons, highscore.txt
└── out/artifacts/...          # JAR pronto (opcional)
```

Todos os `.java` estão no **pacote default** – basta compilar sem declarar `package`.

---

## 🚀 Como Executar

### 1. Usar o JAR pronto

```bash
java -jar "out/artifacts/commando_raid_clone_jar/commando raid clone.jar"
```

> A pasta `res/` deve ficar lado a lado ao JAR, pois o jogo carrega recursos por caminho relativo.

### 2. Compilar do zero

```bash
# Na raiz do projeto
mkdir -p out/production
javac -d out/production src/*.java
java -cp out/production Main
```

Para empacotar:

```bash
echo "Main-Class: Main" > manifest.txt
jar cfm commando-raid.jar manifest.txt -C out/production .
java -jar commando-raid.jar
```

---

## 📈 High-score

* Arquivo `res/highscore.txt` guarda a maior pontuação.
* Se não existir, o jogo cria com valor **0**.
* O diretório/arquivo precisa ter permissão de escrita.

---

## 🖥️ Onde entra Computação Gráfica

| Componente            | Arquivo / Método               | Conceito de CG            | Descrição                                                                                  |
| --------------------- | ------------------------------ | ------------------------- | ------------------------------------------------------------------------------------------ |
| Renderização 2D       | `Game.render()`                | Double-buffering          | Usa `BufferStrategy` para eliminar flicker; `bufferStrategy.show()` após desenhar.         |
| Game-loop             | `Game.run()`                   | Tempo real                | Atualiza lógica (`tick`) → desenha (`render`) com alvo de FPS constante.                   |
| Sprite-sheet          | `SpriteSheet.getSprite()`      | Texturização              | Recorta sub-imagens (`getSubimage`) de um PNG único.                                       |
| Espelhamento          | `SpriteSheet.reverseImage()`   | Transformação afim        | Aplica `AffineTransform` (-1, 1) para inverter sprites de inimigos.                        |
| Rotação do canhão     | `Player.render()`              | Transformação 2D          | `translate` → `rotate` para orientar o tubo em direção ao mouse.                           |
| Alpha-blending        | `SpriteSheet.changeAlpha()`    | Composição alfa           | Usa `AlphaComposite` para gerar fade-out na explosão.                                      |
| Manipulação per-pixel | `SpriteSheet.changeColor()`    | Processamento de imagem   | Itera pixels, altera canais RGB mantendo alfa (ex.: canhão fica vermelho durante recarga). |
| Tiles                 | `TileMap.render()`             | Cenário com grid          | Repete pequenos sprites para formar o chão.                                                |
| Colisão AABB          | Entidades estendem `Rectangle` | Bounding box              | Verifica interseção bala × inimigo, bomba × jogador etc.                                   |
| Animação              | `Explosion.tick()`             | Interpolação de atributos | Diminui opacidade ao longo do tempo gerando efeito visual.                                 |

Esses pontos cobrem fundamentos de CG: representação raster, transformações, render em tempo real e composição visual.

---

## 🛠️ Próximos Passos / TODO

* Migrar build para **Gradle** ou **Maven**.
* Embutir recursos no JAR (`getResourceAsStream`).
* HUD mostrando tempo restante para próxima bomba.
* Revisar concorrência nas coleções (`LinkedBlockingQueue`).
* Adicionar testes unitários (colisões, pontuação).
* Definir e adicionar **licença** (MIT, Apache 2.0 etc.).

---

## 🤝 Contribuindo

1. Fork → `git checkout -b minha-feature`
2. Commit ↔ Push
3. Abra um *Pull Request* descrevendo a mudança.

---

## 📄 Licença

[MIT License](https://opensource.org/licenses/MIT).

---

> Qualquer dúvida ou sugestão, abra uma **Issue** ou entre em contato!
