CSS_SRC    := resources/main.css
CSS_TARGET := resources/public/css/main.css

install:
	@echo ":: Install dependencies"
	yarn install

repl:
	npx shadow-cljs watch dev

$(CSS_TARGET): $(CSS_SRC)
	npx tailwind build $^ -o $@

css: $(CSS_TARGET)
css-watch:
	fsevent_watch -F  | xargs -I{} make $(CSS_TARGET)
