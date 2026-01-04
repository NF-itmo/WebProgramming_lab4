import { BuildOptions, buildWebpack } from '@packages/build-config';
import path from 'path';
import ModuleFederationPlugin from '@module-federation/enhanced';
import webpack from 'webpack';

type EnvVariables = {}

module.exports = (env: EnvVariables, argv: BuildOptions) => {
  const config: webpack.Configuration = buildWebpack({
    mode: argv.mode || 'development',
    port: 3001,
    paths: {
      entry: path.resolve(__dirname, 'src/index'),
      html: path.resolve(__dirname, 'public/index.html'),
      output: path.resolve(__dirname, 'dist'),
      src: path.resolve(__dirname, 'src'),
      public: path.resolve(__dirname, 'public'),
    },
    analyzer: false,
  });

  if (!config.plugins) config.plugins = []

  config.plugins.push(
    new webpack.container.ModuleFederationPlugin({
      name: 'login',
      filename: 'remoteEntry.js',
      exposes: {
        './LoginApp': './src/LoginApp',
      },
      shared: {
        react: {
          eager: true,
          singleton: true,
          requiredVersion: '^19.2.0',
        },
        'react-dom': {
          eager: true,
          singleton: true,
          requiredVersion: '^19.2.0',
        },
        'react-router-dom': {
          eager: true,
          singleton: true,
          requiredVersion: '^7.9.5',
        },
        '@reduxjs/toolkit': {
          eager: true,
          singleton: true,
          requiredVersion: '^2.10.1',
        },
        'react-redux': {
          eager: true,
          singleton: true,
          requiredVersion: '^9.2.0',
        },
        redux: {
          eager: true,
          singleton: true,
          requiredVersion: '^5.0.1',
        },
        '@packages/shared': {
          singleton: true,
          eager: true,
        }
      }
    })
  )

  return config;
};