//
//  RulesListViewController.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Cocoa/Cocoa.h>
#import "AIObject.h"

@interface RulesListViewController : NSViewController
@property (weak) IBOutlet NSScrollView *tableScrollView;
@property (weak) IBOutlet NSTableView *tableView;
@property (weak) IBOutlet NSButton *backButton;
@property (weak) IBOutlet NSImageView *background;
@property (weak) IBOutlet NSButton *modifyButton;
@property (weak) IBOutlet NSButton *saveButton;
@property (weak) IBOutlet NSButton *moveUpButton;
@property (weak) IBOutlet NSButton *moveDownButton;
@property (weak) IBOutlet NSButton *addNewButton;
@property (weak) IBOutlet NSTextField *aiName;

- (void)loadAI:(AIObject *)aiObject;

@end
