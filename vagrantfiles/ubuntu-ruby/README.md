# ubuntu-ruby

```
vagrant up

# # optional
# vagrant ssh-config >> ~/.ssh/config

itamae ssh --vagrant -l debug recipe.rb
```

## .bashrc

Restriction of my plugin.

```
export RBENV_ROOT=/usr/local/rbenv
export PATH="${RBENV_ROOT}/bin:${PATH}"
eval "$(rbenv init -)"
```
