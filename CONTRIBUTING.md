# Contributing to mitamae
Please report bugs to GitHub issues, and submit patches as a pull request.

Currently this page mainly describes how to develop mitamae.

## Development

```bash
$ rake compile && ./mruby/bin/mitamae local recipe.rb

# If you add mrbgem to mrbgem.rake, execute:
$ rake clean
```

## Testing

```bash
# Run integration tests on Docker
$ bundle exec rspec
```

## Cross compile

```bash
# Compile and copy binaries to ./mitamae-build
$ rake release:build
```

## Release

```bash
$ git commit -m "Version vX.X.X"
$ git tag vX.X.X
$ git push origin --tags # released here
$ git push origin master
```
