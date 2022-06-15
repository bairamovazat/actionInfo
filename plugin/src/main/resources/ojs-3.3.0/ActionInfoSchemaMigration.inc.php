<?php

/**
 * @file StaticPagesSchemaMigration.inc.php
 *
 * Copyright (c) 2014-2020 Simon Fraser University
 * Copyright (c) 2000-2020 John Willinsky
 * Distributed under the GNU GPL v3. For full terms see the file docs/COPYING.
 *
 * @class StaticPagesSchemaMigration
 * @brief Describe database table structures.
 */

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Builder;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Capsule\Manager as Capsule;

class ActionInfoSchemaMigration extends Migration
{
    /**
     * Run the migrations.
     */
    public function up()
    {
        // List of static pages for each context
        Capsule::schema()->create('action_info', function (Blueprint $table) {
            $table->bigInteger('id')->autoIncrement();
            $table->string('type', 255);
            $table->string('action', 255);
            $table->string('params', 255);
            $table->text('payload');
            $table->bigInteger('date');
            $table->bigInteger('user_id');
            $table->bigInteger('context_id');
            $table->index(['user_id'], 'users_user_id');
            $table->index(['context_id'], 'journals_journal_id');
        });

    }

//    /**
//     * Reverse the migration.
//     */
//    public function down(): void
//    {
//        Schema::drop('action_info');
//    }
}
