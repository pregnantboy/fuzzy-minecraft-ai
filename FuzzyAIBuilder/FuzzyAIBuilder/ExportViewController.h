//
//  ExportViewController.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 31/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Cocoa/Cocoa.h>
#import "AIObject.h"

@interface ExportViewController : NSViewController
@property (weak) IBOutlet NSImageView *background;
@property (weak) IBOutlet NSButton *backButton;
@property (weak) IBOutlet NSTextField *name;
@property (weak) IBOutlet NSButton *slot1;
@property (weak) IBOutlet NSButton *slot2;
@property (weak) IBOutlet NSButton *slot3;
@property (weak) IBOutlet NSButton *slot4;

- (void)loadAI:(AIObject *)ai;

@end
