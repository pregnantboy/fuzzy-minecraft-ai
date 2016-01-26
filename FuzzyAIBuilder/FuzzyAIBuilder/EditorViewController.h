//
//  EditorViewController.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Cocoa/Cocoa.h>
#import "Rule.h"
#import "RulePopUpButton.h"

@interface EditorViewController : NSViewController
@property (weak) IBOutlet NSButton *backButton;
@property (weak) IBOutlet NSTableView *inputTableView;
@property (weak) IBOutlet NSImageView *background;
@property (weak) IBOutlet NSButton *addButton;
@property (weak) IBOutlet NSButton *deleteButton;
@property (weak) IBOutlet RulePopUpButton *outcome1;
@property (weak) IBOutlet RulePopUpButton *outcome2;

@end
